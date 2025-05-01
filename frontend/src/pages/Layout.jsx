import {Outlet} from "react-router-dom";
import ConmedNavBar from "../components/basic/layout/ConmedNavBar.jsx";

function Layout() {
    return (
        <>
            <div className="d-flex flex-column" style={{ minHeight: '100vh' }}>
                <ConmedNavBar/>
                <Outlet/>
            </div>
        </>
    )
}

export default Layout;
