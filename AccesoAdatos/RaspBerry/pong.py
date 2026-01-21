import pygame
import sys
import random
import math

pygame.init()

# ---------------- CONFIG ----------------
WIDTH, HEIGHT = 1000, 680
screen = pygame.display.set_mode((WIDTH, HEIGHT))
pygame.display.set_caption("Pong Roguelike Timed Mods")

clock = pygame.time.Clock()
font = pygame.font.Font(None, 26)
big_font = pygame.font.Font(None, 54)

WHITE = (255, 255, 255)
BLACK = (0, 0, 0)

# ---------------- PLAYER & AI ----------------
paddle_w, paddle_h = 10, 80
player = pygame.Rect(20, HEIGHT//2 - paddle_h//2, paddle_w, paddle_h)
ai = pygame.Rect(WIDTH - 30, HEIGHT//2 - paddle_h//2, paddle_w, paddle_h) 

paddle_base_speed = 6
ai_base_speed = 3
# current speeds (ai_speed is modified by level ups elsewhere)
paddle_speed = paddle_base_speed
ai_speed = ai_base_speed

# ---------------- GAME STATS ----------------
lives = 3
score = 0
level = 1
game_over = False

# Game state: 'menu', 'playing', 'store'
state = 'menu'
menu_selected = 0
menu_options = ['Jugar', 'Tienda']
last_score = None

# IA humana
# Empezar con un error grande en nivel 1 para que sea "basto" y luego mejore
ai_error_chance = 0.6
ai_reaction_delay = 10
ai_timer = 0
ai_target_y = ai.centery

# Tiempo de mutadores
modifier_timer = 0
MODIFIER_INTERVAL = 10 * 1000  # cada 10 segundos en ms

# Pickups de modificadores en pantalla
modifier_pickups = []

# ---------------- BALL ----------------
class Ball:
    def __init__(self, direction=1, offset_y=0, x=None, y=None, vx=None, vy=None):
        # Allow creating a ball at a specific position and velocity
        # Use a base radius so modifiers can change visual/collision size
        self.base_radius = 5
        self.radius = self.base_radius

        if x is None or y is None:
            cx = WIDTH // 2
            cy = HEIGHT // 2 + offset_y
        else:
            cx = int(x)
            cy = int(y)

        # Rect is kept in sync with radius (width/height = 2*radius)
        self.rect = pygame.Rect(int(cx - self.radius), int(cy - self.radius), self.radius * 2, self.radius * 2)

        if vx is None or vy is None:
            speed = random.uniform(3.5, 4.5)
            angle = random.uniform(-0.6, 0.6)
            self.vx = speed * direction
            self.vy = speed * angle
        else:
            self.vx = vx
            self.vy = vy

    def update(self, modifiers):
        # Reset visual/collision size to base every frame; modifiers can change it
        self.radius = self.base_radius
        for m in modifiers:
            m.apply_ball(self)

        # Keep rect centered when radius changes
        centerx, centery = self.rect.centerx, self.rect.centery
        self.rect.width = int(self.radius * 2)
        self.rect.height = int(self.radius * 2)
        self.rect.centerx = centerx
        self.rect.centery = centery

        # Continuous collision detection to avoid tunneling:
        # compute start and end centers
        start_cx = float(self.rect.centerx)
        start_cy = float(self.rect.centery)
        dx = float(self.vx)
        dy = float(self.vy)

        def liang_barsky(p0x, p0y, dx, dy, rect):
            # Liang-Barsky algorithm for segment AABB intersection.
            p = [-dx, dx, -dy, dy]
            q = [p0x - rect.left, rect.right - p0x, p0y - rect.top, rect.bottom - p0y]
            u1 = 0.0
            u2 = 1.0
            for pi, qi in zip(p, q):
                if pi == 0:
                    if qi < 0:
                        return None
                    else:
                        continue
                t = qi / pi
                if pi < 0:
                    if t > u2:
                        return None
                    if t > u1:
                        u1 = t
                else:
                    if t < u1:
                        return None
                    if t < u2:
                        u2 = t
            if u1 <= u2 and 0.0 <= u1 <= 1.0:
                return u1
            return None

        collided = False

        # Only check paddle collisions if modifiers allow
        for m in modifiers:
            if not m.allow_paddle_collision():
                break
        else:
            # check player then ai for swept intersection
            for paddle in (player, ai):
                inf_rect = paddle.inflate(int(self.radius * 2), int(self.radius * 2))
                t = liang_barsky(start_cx, start_cy, dx, dy, inf_rect)
                if t is not None:
                    # move to collision point (center of ball touching inflated rect)
                    col_x = start_cx + dx * t
                    col_y = start_cy + dy * t
                    self.rect.centerx = int(col_x)
                    self.rect.centery = int(col_y)
                    # compute new velocity via bounce
                    self.bounce_off_paddle(paddle)
                    # move remaining distance after collision using new velocity
                    remaining = 1.0 - t
                    self.rect.x += int(self.vx * remaining)
                    self.rect.y += int(self.vy * remaining)
                    collided = True
                    break

        if not collided:
            self.rect.x += int(self.vx)
            self.rect.y += int(self.vy)

        if self.rect.top <= 0 or self.rect.bottom >= HEIGHT:
            self.vy *= -1

    def draw(self):
        # Draw circle matching the current radius for visual fidelity
        pygame.draw.circle(screen, WHITE, (int(self.rect.centerx), int(self.rect.centery)), int(self.radius))

    def bounce_off_paddle(self, paddle):
        # Calculate hit position relative to paddle center (-1 .. 1)

        # Calculate hit position relative to paddle center (-1 .. 1)
        relative_y = (self.rect.centery - paddle.centery) / (paddle.height / 2)
        relative_y = max(-1.0, min(1.0, relative_y))

        # Apply a progressive curve mapping impact -> angle.
        # To reduce the wide 'flat' central zone and make slight offsets
        # produce more angled bounces, use a power < 1 (makes the curve
        # steeper near center). We also slightly increase max angle so
        # the paddle edges remain effective.
        # Punto medio entre sensación 'plana' y 'demasiado sensible'
        curve_power = 1.1
        sign = 1 if relative_y >= 0 else -1
        progressive = sign * (abs(relative_y) ** curve_power)

        # Max bounce angle (radians) — a bit larger to keep sides influential
        max_angle = math.radians(55)
        angle = progressive * max_angle

        # Preserve speed magnitude, then slightly increase it on each paddle hit
        speed = max(2.0, (self.vx ** 2 + self.vy ** 2) ** 0.5)
        # Increase speed by a small factor per hit to make rallies escalate
        # Slightly faster growth requested → 6% per hit
        speed_increase_factor = 1.06
        # Optional cap to avoid runaway speeds
        max_speed_cap = 30.0
        new_speed = min(speed * speed_increase_factor, max_speed_cap)

        # Determine direction based on paddle side and apply new speed
        if paddle.centerx < WIDTH / 2:
            # left paddle -> send ball to the right
            self.vx = abs(new_speed * math.cos(angle))
        else:
            # right paddle -> send ball to the left
            self.vx = -abs(new_speed * math.cos(angle))

        self.vy = new_speed * math.sin(angle)

        # Nudge ball out of paddle to avoid repeated collision
        if self.vx > 0:
            self.rect.left = paddle.right + 1
        else:
            self.rect.right = paddle.left - 1

balls = []

def reset_round(direction=1):
    balls.clear()
    # Si doble bola activa → una bola a la izquierda y otra a la derecha
    if any(isinstance(m, DoubleBall) for m in active_modifiers):
        balls.append(Ball(direction=1, offset_y=-20))
        balls.append(Ball(direction=-1, offset_y=20))
    else:
        balls.append(Ball(direction=direction, offset_y=0))

def reset_game():
    global lives, score, level, game_over
    global ai_speed, ai_error_chance, modifier_timer

    lives = 3
    score = 0
    level = 1
    game_over = False

    ai_speed = 3
    # reiniciar a error alto por defecto (nivel 1)
    ai_error_chance = 0.6
    # reiniciar retraso de reacción también
    global ai_reaction_delay
    ai_reaction_delay = 10
    modifier_timer = pygame.time.get_ticks()

    active_modifiers.clear()
    modifier_pickups.clear()
    # Sistema de niveles: puntos necesarios para subir de nivel y progreso en el nivel
    global level_target_points, score_in_level
    level_target_points = 3  # puntos necesarios en el nivel 1
    score_in_level = 0
    reset_round(direction=1)

# ---------------- MODIFIERS ----------------
class Modifier:
    def __init__(self, name, duration):
        self.name = name
        self.duration = duration
        self.start_time = pygame.time.get_ticks()

    def expired(self):
        return (pygame.time.get_ticks() - self.start_time) / 1000 >= self.duration

    def remaining(self):
        elapsed = (pygame.time.get_ticks() - self.start_time) / 1000
        return max(0, int(self.duration - elapsed))

    def apply_ball(self, ball): pass
    def allow_paddle_collision(self): return True
    def control_inverted(self): return False

class DoubleBall(Modifier):
    def __init__(self):
        super().__init__("Doble Bola", 10)

class Gravity(Modifier):
    def __init__(self):
        super().__init__("Gravedad", 12)

    def apply_ball(self, ball):
        ball.vy += 0.15

class FastBall(Modifier):
    def __init__(self):
        super().__init__("Velocidad Extrema", 8)

    def apply_ball(self, ball):
        ball.vx *= 1.01
        ball.vy *= 1.01

class GhostBall(Modifier):
    def __init__(self):
        super().__init__("Bola Fantasma", 8)

    def allow_paddle_collision(self):
        return random.random() > 0.4

class InvertControls(Modifier):
    def __init__(self):
        super().__init__("Controles Invertidos", 6)

    def control_inverted(self):
        return True

class BigBalls(Modifier):
    def __init__(self):
        super().__init__("Bolas Grandes", 15)

    def apply_ball(self, ball):
        ball.radius += 12  # Increase the ball size

modifier_classes = [
    DoubleBall,
    Gravity,
    FastBall,
    GhostBall,
    InvertControls,
    BigBalls
]

active_modifiers = []


class ModifierPickup:
    def __init__(self, mod_class):
        self.mod_class = mod_class
        margin = 60
        self.x = random.randint(margin, WIDTH - margin)
        self.y = random.randint(margin, HEIGHT - margin)
        self.radius = 18

        # symbol mapping
        name = mod_class().__class__.__name__
        if name == 'DoubleBall':
            self.symbol = '2'
            self.color = (200, 180, 50)
        elif name == 'Gravity':
            self.symbol = 'G'
            self.color = (150, 50, 200)
        elif name == 'FastBall':
            self.symbol = '>'
            self.color = (220, 60, 60)
        elif name == 'GhostBall':
            self.symbol = '?'
            self.color = (120, 200, 220)
        elif name == 'InvertControls':
            self.symbol = 'I'
            self.color = (80, 220, 120)
        elif name == 'BigBalls':
            self.symbol = 'B'
            self.color = (200, 100, 100)
        else:
            self.symbol = '*'
            self.color = (200, 200, 200)
        

    def draw(self):
        pygame.draw.circle(screen, self.color, (self.x, self.y), self.radius)
        txt = font.render(self.symbol, True, BLACK)
        txt_rect = txt.get_rect(center=(self.x, self.y))
        screen.blit(txt, txt_rect)

    def check_collision(self, ball_rect):
        # ball center
        bx = ball_rect.centerx
        by = ball_rect.centery
        dx = bx - self.x
        dy = by - self.y
        dist2 = dx*dx + dy*dy
        return dist2 <= (self.radius + max(ball_rect.width, ball_rect.height)//2) ** 2

def add_random_modifier():
    # En lugar de activarlo inmediatamente, creamos un pickup en pantalla
    mod_class = random.choice(modifier_classes)
    # limitar número de pickups simultáneos
    if len(modifier_pickups) < 3:
        modifier_pickups.append(ModifierPickup(mod_class))

# ---------------- INIT ----------------
reset_game()

# ---------------- MAIN LOOP ----------------
while True:
    now = pygame.time.get_ticks()
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            pygame.quit()
            sys.exit()

        if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_r:
                    reset_game()
                # Menu navigation when not playing
                if state == 'menu':
                    if event.key == pygame.K_w:
                        menu_selected = (menu_selected - 1) % len(menu_options)
                    elif event.key == pygame.K_s:
                        menu_selected = (menu_selected + 1) % len(menu_options)
                    elif event.key == pygame.K_RETURN or event.key == pygame.K_KP_ENTER:
                        choice = menu_options[menu_selected]
                        if choice == 'Jugar':
                            state = 'playing'
                            reset_game()
                            last_score = None
                        elif choice == 'Tienda':
                            state = 'store'
                elif state == 'store':
                    # simple placeholder: ESC to go back to menu
                    if event.key == pygame.K_ESCAPE:
                        state = 'menu'

    screen.fill(BLACK)

    # --- Menu / Store screens ---
    if state == 'menu':
        title = big_font.render("Pong Roguelike", True, WHITE)
        screen.blit(title, (WIDTH//2 - title.get_width()//2, 120))
        if last_score is not None:
            last_txt = font.render(f"Última puntuación: {last_score}", True, WHITE)
            screen.blit(last_txt, (WIDTH//2 - last_txt.get_width()//2, 180))
        opt_y = 260
        for i, opt in enumerate(menu_options):
            color = (200, 200, 60) if i == menu_selected else WHITE
            txt = big_font.render(opt, True, color)
            screen.blit(txt, (WIDTH//2 - txt.get_width()//2, opt_y))
            opt_y += 70

        hint = font.render("Usa ARRIBA/ABAJO y ENTER. Tienda: placeholder.", True, WHITE)
        screen.blit(hint, (WIDTH//2 - hint.get_width()//2, opt_y + 10))

    elif state == 'store':
        title = big_font.render("Tienda (en desarrollo)", True, WHITE)
        screen.blit(title, (WIDTH//2 - title.get_width()//2, HEIGHT//2 - 30))
        hint = font.render("Pulse ESC para volver al menú", True, WHITE)
        screen.blit(hint, (WIDTH//2 - hint.get_width()//2, HEIGHT//2 + 40))

    else:
        # gameplay
        if not game_over:
        # Compute current ball speed (max of balls) to scale paddle speeds
            if balls:
                ball_speed = max(((b.vx ** 2 + b.vy ** 2) ** 0.5) for b in balls)
            else:
                ball_speed = 4.0

        # Speed bonus (how much faster than baseline)
        speed_bonus = max(0.0, ball_speed - 4.0)

        # Effective player and AI speeds scale with ball speed
        player_effective_speed = paddle_speed + min(speed_bonus * 0.9, 8)
        ai_effective_speed = ai_speed + min(speed_bonus * 0.8, 8)

        # -------- INPUT --------
        keys = pygame.key.get_pressed()
        inverted = any(m.control_inverted() for m in active_modifiers)

        direction = 0
        if keys[pygame.K_w]:
            direction -= 1
        if keys[pygame.K_s]:
            direction += 1

        if inverted:
            direction *= -1

        player.y += direction * player_effective_speed
        player.y = max(0, min(HEIGHT - player.height, player.y))

        # -------- AI --------
        ai_timer += 1

        # Elegir la bola más cercana horizontalmente a la IA (soporta múltiples bolas)
        if balls:
            main_ball = min(balls, key=lambda b: abs(b.rect.centerx - ai.centerx))
        else:
            main_ball = None

        if ai_timer >= ai_reaction_delay:
            ai_timer = 0
            if random.random() < ai_error_chance:
                ai_target_y = random.randint(0, HEIGHT)
            else:
                ai_target_y = main_ball.rect.centery if main_ball is not None else ai.centery

        if ai.centery < ai_target_y:
            ai.y += ai_effective_speed
        elif ai.centery > ai_target_y:
            ai.y -= ai_effective_speed

        # -------- BALLS --------
        for ball in balls[:]:
            ball.update(active_modifiers)

            if ball.rect.right >= WIDTH:
                # Punto al enemigo (marcador total)
                score += 1
                # Progreso dentro del nivel
                score_in_level += 1

                # Si se alcanza el objetivo del nivel -> subir nivel y aumentar dificultad
                if score_in_level >= level_target_points:
                    level += 1
                    score_in_level = 0
                    # aumentar objetivo para el siguiente nivel (más difícil)
                    level_target_points += 2
                    # Aumentar dificultad: AI más rápida, menos error y mejor reacción
                    ai_speed += 0.6
                    # Reducir error multiplicativamente para una progresión más natural
                    ai_error_chance = max(0.02, ai_error_chance * 0.78)
                    # Curar 1 vida al subir de nivel
                    lives += 1
                    # Mejorar tiempo de reacción ligeramente (más "lista")
                    ai_reaction_delay = max(2, ai_reaction_delay - 1)

                reset_round(-1)
                break

            if ball.rect.left <= 0:
                lives -= 1
                # Si se acaban las vidas => guardar puntuación final, reiniciar y volver al menú
                if lives <= 0:
                    last_score = score
                    reset_game()
                    state = 'menu'
                    menu_selected = 0
                else:
                    reset_round(1)
                break

        # -------- PICKUP COLLISIONS --------
        for ball in balls[:]:
            for pu in modifier_pickups[:]:
                if pu.check_collision(ball.rect):
                    new_mod = pu.mod_class()
                    active_modifiers.append(new_mod)
                    # Si es DoubleBall, generamos la nueva bola en la posición de la actual con ángulo diferente
                    if isinstance(new_mod, DoubleBall):
                        bx, by = ball.rect.centerx, ball.rect.centery
                        speed = (ball.vx ** 2 + ball.vy ** 2) ** 0.5
                        angle = math.atan2(ball.vy, ball.vx)
                        # cambiar ángulo aleatoriamente entre 30º y 70º en sentido aleatorio
                        delta = random.uniform(0.52, 1.22) * random.choice([-1, 1])
                        new_angle = angle + delta
                        nvx = speed * math.cos(new_angle)
                        nvy = speed * math.sin(new_angle)
                        # crear la nueva bola junto a la actual (no eliminamos la existente)
                        balls.append(Ball(x=bx, y=by, vx=nvx, vy=nvy))
                    try:
                        modifier_pickups.remove(pu)
                    except ValueError:
                        pass

        # -------- MODIFIERS AUTOMÁTICOS --------
        if now - modifier_timer >= MODIFIER_INTERVAL:
            add_random_modifier()
            modifier_timer = now

        # -------- CLEAN MODIFIERS --------
        before = len(active_modifiers)
        active_modifiers = [m for m in active_modifiers if not m.expired()]

        # Si termina doble bola → volver a una sola
        if before != len(active_modifiers):
            if not any(isinstance(m, DoubleBall) for m in active_modifiers):
                balls[:] = balls[:1]

    # ---------------- DRAW ----------------
    pygame.draw.rect(screen, WHITE, player)
    pygame.draw.rect(screen, WHITE, ai)

    # Draw modifier pickups
    for pu in modifier_pickups:
        pu.draw()

    for ball in balls:
        ball.draw()

    hud = font.render(
        f"Puntos: {score} | Vidas: {lives} | Nivel: {level} ({score_in_level}/{level_target_points})", True, WHITE
    )
    screen.blit(hud, (20, 10))

    y = 40
    for m in active_modifiers:
        txt = font.render(f"{m.name} ({m.remaining()}s)", True, WHITE)
        screen.blit(txt, (20, y))
        y += 20

    restart_text = font.render("Pulsa R para reiniciar", True, WHITE)
    screen.blit(restart_text, (WIDTH - restart_text.get_width() - 20, HEIGHT - 30))

    if game_over:
        over = big_font.render("GAME OVER", True, WHITE)
        final = font.render(f"Puntuación final: {score}", True, WHITE)
        screen.blit(over, (WIDTH//2 - over.get_width()//2, HEIGHT//2 - 40))
        screen.blit(final, (WIDTH//2 - final.get_width()//2, HEIGHT//2 + 20))

    pygame.display.flip()
    clock.tick(60)
