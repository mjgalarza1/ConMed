import Container from "react-bootstrap/Container";
import Navbar from "react-bootstrap/Navbar";
import ConmedLogo from "./ConmedLogo.jsx";

function ConmedNavBar() {
    return (
        <>
            <Navbar expand="lg" className="bg-body-tertiary">
                <Container>
                    <Navbar.Brand href="/">
                        <ConmedLogo/>
                    </Navbar.Brand>
                </Container>
            </Navbar>
        </>
    )
}

export default ConmedNavBar;
