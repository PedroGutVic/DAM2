const StatusMessage = ({ isOnline }) => (
    <p className={isOnline ? "text-success" : "text-danger"}>
        {isOnline ? "Usuario en línea" : "Usuario no en línea"}
    </p>
);
export default StatusMessage