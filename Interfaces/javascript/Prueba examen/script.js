const gifts = [];

const inputGift = document.getElementById('gift');
const sendButton = document.getElementById('send');
const giftList = document.getElementById('giftList');
const deleteAllButton = document.getElementById('deleteAll')


sendButton.addEventListener('click', ()=> {
    const newGift = inputGift.value.trim();
    if (newGift.length > 0) {
        gifts.push(newGift)
        renderGifts();
        inputGift.value;
    }
});

deleteAllButton.addEventListener('click', () => {
    gifts = [];
    giftList.innerHTML = '';
})

function renderGifts() {
    giftList.innerHTML = '';
    gifts.forEach((gift)=>{
        const html = `<li>${gift}</li>`;
        giftList.innerHTML += html;
    });
}