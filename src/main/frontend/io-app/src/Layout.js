import {Link, Outlet} from "react-router-dom";

function Layout() {

    const userFromLocalStorage = JSON.parse(localStorage.getItem("USER")) ?? null;

    return (
        <div>
            <nav className="navbar navbar-expand bg-light">
                <div className="container-fluid">
                    <ul className="navbar-nav">
                        <li className="nav-item me-2"><Link to="/" className="nav-link">Strona główna</Link></li>
                        {userFromLocalStorage && userFromLocalStorage.role === "PROWADZACY" ?
                            (<li className="nav-item me-2">
                                <Link to={"/classes"} className="nav-link">Moje zajęcia</Link>
                            </li>) : (<></>)}
                        {userFromLocalStorage ?
                            (<li className="nav-item me-2"><Link to="/login" className="nav-link">Moje konto</Link></li>) :
                            (<li className="nav-item me-2"><Link to="/login" className="nav-link">Logowanie</Link></li>)
                        }
                    </ul>
                </div>
            </nav>
            <Outlet />
        </div>
    )
}

export default Layout;