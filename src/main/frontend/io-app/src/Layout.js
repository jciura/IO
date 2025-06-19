import { Link, Outlet } from "react-router-dom";

function Layout() {
    const user = JSON.parse(localStorage.getItem("USER")) ?? null;

    return (
        <div>
            <nav className="navbar navbar-expand bg-light">
                <div className="container-fluid">
                    <ul className="navbar-nav">
                        <li className="nav-item me-2">
                            <Link to="/" className="nav-link">Strona główna</Link>
                        </li>

                        {user && (user.role === "PROWADZACY" || user.role === "STAROSTA") && (
                            <li className="nav-item me-2">
                                <Link to="/classes" className="nav-link">Moje zajęcia</Link>
                            </li>
                        )}

                        {user && user.role === "STAROSTA" && (
                            <li className="nav-item me-2">
                                <Link to="/confirmations" className="nav-link">Prośby o zmianę</Link>
                            </li>
                        )}

                        {user && user.role === "ADMINISTRATOR" && (
                            <>
                                <li className="nav-item me-2">
                                    <Link to="/admin-panel" className="nav-link">Sale</Link>
                                </li>
                                <li className="nav-item me-2">
                                    <Link to="/course-panel" className="nav-link">Dodaj kurs</Link>
                                </li>
                                <li className="nav-item me-2">
                                    <Link to="/software-panel" className="nav-link">Dodaj oprogramowanie</Link>
                                </li>
                            </>
                        )}

                        {user && user.role === "KOORDYNATOR" && (
                            <li className="nav-item me-2">
                                <Link to="/timetables" className="nav-link">Plany zajęć</Link>
                            </li>
                        )}

                        <li className="nav-item me-2">
                            <Link to="/login" className="nav-link">
                                {user ? "Moje konto" : "Logowanie"}
                            </Link>
                        </li>
                    </ul>
                </div>
            </nav>
            <Outlet />
        </div>
    );
}

export default Layout;
