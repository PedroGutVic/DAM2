const ItemList = ({ items = [] }) => (
    <ul className="list-group mb-3">
        {items.map((item, index) => (
            <li key={index} className="list-group-item">
                {item}
            </li>
        ))}
    </ul>
);

export default ItemList;