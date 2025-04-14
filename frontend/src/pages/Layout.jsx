import {Outlet} from "react-router-dom";
import ConmedLogo from "../components/basic/ConmedLogo.jsx";

function Layout() {
    return (
        <>
            <div style={{position: 'absolute', top: '20px', left: '20px'}}>
                <ConmedLogo/>
            </div>
            <Outlet/>
        </>
    )
}

export default Layout;
