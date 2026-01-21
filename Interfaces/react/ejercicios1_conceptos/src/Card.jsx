import Card from 'react-bootstrap/Card';

const BasicCard = ({ titulo, children }) => (
    <Card className="shadow-sm mb-3">
        <Card.Header>
            <h3 className="m-0 text-primary">{titulo}</h3>
        </Card.Header>
        <Card.Body>
            <h5>{children}</h5>
        </Card.Body>
        <Card.Footer>
            <small className="text-muted">{children}</small>
        </Card.Footer>
    </Card>
);
export default BasicCard;