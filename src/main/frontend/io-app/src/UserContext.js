import {createContext, useState} from "react";

export const UserContext = createContext();

export const UserProvider = ({children}) => {
    const [user, setUser] = useState(
        localStorage.getItem("USER") ? JSON.parse(localStorage.getItem("USER")) : null
    );

    return (
        <UserContext.Provider value={{user, setUser}} >
            {children}
        </UserContext.Provider>
    )
}


