const Image = ({ src, alt, width, description }) => {
    return (
        <>
            <img src={src} width={width} alt={alt}></img>

            <p>{description}</p>
        </>
    )
}
export default Image;