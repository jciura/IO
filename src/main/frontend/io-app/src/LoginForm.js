import {useContext, useEffect, useState} from "react";
import {UserContext} from "./UserContext";
import {API_URL} from './config';

function LoginForm() {
    var userFromLocalStorage = JSON.parse(localStorage.getItem("USER")) ?? null;
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [isSuccessful, setIsSuccessful] = useState(false);
    const [token, setToken] = useState("");  //TODO: Obsługa tokenów
    const [info, setInfo] = useState(null);

    const {user, setUser} = useContext(UserContext);

    useEffect(() => {

    }, [isSuccessful]);

    async function handleLogin() {
        try {
            const response = await fetch(`${API_URL}/users/login`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    email,
                    password
                })
            });

            if (response.ok) {
                const data = await response.json();
                console.log("Logged in successfully");
                setIsSuccessful(true);
                localStorage.setItem("USER", JSON.stringify(data));
                setUser(data);
                userFromLocalStorage = data;
                console.log(data.role);
                console.log(localStorage.getItem("USER"));
            } else if (response.status === 400) {
                console.log("Wrong password!");
                setInfo("Wrong password!");
            } else {
                console.log("Wrong password or email.");
                setInfo("Wrong password or email!");
            }
        } catch (error) {
            console.log("Error during login: ", error);
        }
    }

    function handleLogout() {
        setIsSuccessful(false);
        localStorage.removeItem("USER");
        setUser(null);
        userFromLocalStorage = null;
    }

    // function parseJwt (token) {
    //     var base64Url = token.split('.')[1];
    //     var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    //     var jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function(c) {
    //         return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    //     }).join(''));
    //
    //     return JSON.parse(jsonPayload);
    // }

    return (
        <div>
            <div className="justify-content-center p-4 d-flex">
                {!isSuccessful && !userFromLocalStorage ? (
                    <div className="d-flex flex-column" >
                        <div className="bg-light rounded-3 px-4 py-3 mb-3">
                        {/*<form>*/}
                            <h1>Zaloguj się</h1>
                            <hr></hr>
                            <div className="mt-3">
                                <label for="emailInput" className="form-label mb-3 me-3">Email:</label>
                                <input id="emailInput" type="text" value={email} onChange={(event) => {setEmail(event.target.value)}} />
                            </div>
                            <div>
                                <label for="passwordInput" className="form-label mb-3 me-3">Hasło:</label>
                                <input id="passwordInput" type="password" value={password} onChange={(event) => {setPassword(event.target.value)}} />
                            </div>
                            <button className="btn btn-primary" onClick={handleLogin}>Zaloguj się</button>
                            {/*</form>*/}
                        </div>
                        {(info != null) ? (<p style={{color: 'red'}}>{info}</p>) : (<></>)}
                    </div>
                ) : (
                    <div className="bg-light rounded-3 px-4 py-3 mb-3">
                        <h1>Zalogowany!</h1>
                        <hr></hr>
                        <div>
                            <p><b>Imię:</b> {userFromLocalStorage.firstName}</p>
                            <p><b>Nazwisko:</b> {userFromLocalStorage.lastName}</p>
                            <p><b>Email:</b> {userFromLocalStorage.email}</p>
                            <p><b>Rola:</b> {userFromLocalStorage.role}</p>
                        </div>
                        <button onClick={handleLogout} className="btn btn-primary">Wyloguj</button>
                    </div>
                )}
            </div>
        </div>
    )
}

export default LoginForm;