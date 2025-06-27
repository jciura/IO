import {useEffect, useState} from "react";
import ClassroomCard from "./ClassroomCard";
import Popup from "reactjs-popup";
import {API_URL} from "../config";

function AdminPanel() {
    const userFromLocalStorage = JSON.parse(localStorage.getItem("USER")) ?? null;
    const userId = userFromLocalStorage ? userFromLocalStorage.id : null;
    const [classrooms, setClassrooms] = useState([]);
    const [newClassroomNumber, setNewClassroomNumber] = useState(0);
    const [newBuilding, setNewBuilding] = useState("");
    const [newCapacity, setNewCapacity] = useState(0);
    const [newFloor, setNewFloor] = useState(0);
    const [newHasComputers, setNewHasComputers] = useState(false);

    useEffect(() => {
        const fetchData = async () => {
            if (userId == null)
                return
            try {
                const response = await fetch(`${API_URL}/classrooms`, {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json"
                    }
                });
                if (response.ok) {
                    const data = await response.json();
                    setClassrooms(data);
                    console.log("Sale: ", data);
                }
            } catch (error) {
                console.log("Error while loading classrooms: ", error);
            }
        };
        fetchData();
    }, [userId]);

    async function showClassrooms() {
        try {
            const response = await fetch(`${API_URL}/classrooms`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json"
                }
            });
            if (response.ok) {
                const data = await response.json();
                setClassrooms(data);
                console.log("Sale: ", data);
            }
        } catch (error) {
            console.log("Error while loading classrooms: ", error);
        }
    }

    async function handleAddClassroom() {
        const response = await fetch(`${API_URL}/classrooms`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                building: newBuilding,
                number: newClassroomNumber,
                floor: newFloor,
                capacity: newCapacity,
                hasComputers: newHasComputers,
                softwareNames: []
            })
        })
        const newClassroom = await response.json();
        setClassrooms(prev => [...prev, newClassroom]);
    }

    return (
        <div className="p-4">
            <div className="row">
                <div className="col">
                    <h1>Sale na uczelni</h1>
                </div>
                <div className="col text-end">
                    <Popup trigger={<button className="btn btn-lg" style={{backgroundColor: "lightblue"}}>Dodaj
                        salę</button>} modal nested>
                        {
                            close => (
                                <div className="p-3">
                                    <h1>Dodaj salę</h1>
                                    <hr></hr>
                                    <div className="row">
                                        <div className="col">
                                            <label htmlFor="numberInput" className="mb-3"><b>Numer sali:</b></label><br/>
                                            <label htmlFor="buildingInput" className="mb-3"><b>Budynek:</b></label><br/>
                                            <label htmlFor="capacityInput" className="mb-3"><b>Pojemność:</b></label><br/>
                                            <label htmlFor="floorInput" className="mb-3"><b>Piętro:</b></label><br/>
                                            <label htmlFor="hasComputersInput" className="mb-3"><b>Czy są komputery?</b></label><br/>
                                            {/*<label htmlFor="softwareInput"><b>Dostępne oprogramowanie:</b></label>*/}
                                        </div>
                                        <div className="col">
                                            <input id="numberInput" type="number" className="mb-2"
                                                   onChange={(event) => setNewClassroomNumber(event.target.value)}/><br/>
                                            <input id="buildingInput" type="text" className="mb-2"
                                                   onChange={(event) => setNewBuilding(event.target.value)}/><br/>
                                            <input id="capacityInput" type="number" className="mb-2"
                                                   min={0} max={300}
                                                   onChange={(event) => setNewCapacity(event.target.value)}/><br/>
                                            <input id="floorInput" type="number" className="mb-2"
                                                   defaultValue={0} min={-1} max={7}
                                                   onChange={(event) => setNewFloor(event.target.value)}/><br/>
                                            <input id="hasComputersInput" type="checkbox" className="mb-2 mt-3"
                                                   onChange={(event) => setNewHasComputers(event.target.checked)}/>

                                        </div>
                                    </div>
                                    <div className="d-flex">
                                        <button onClick={()=>{handleAddClassroom(); close()}} className="btn btn-outline-secondary">Dodaj</button>
                                    </div>

                                </div>
                            )
                        }
                    </Popup>

                </div>
            </div>


            <hr></hr>
            <div className="row mb-5">
                {classrooms.length > 0 ? (
                    classrooms.map((classroom, index) =>
                        <div className="col-4 mb-3" key={index}>
                            <ClassroomCard classroom={classroom} onUpdate={showClassrooms}/>
                        </div>
                    )
                ) : (
                    <div className="p-3">
                        <p>Nie ma sal!</p>
                    </div>
                )}
            </div>
        </div>
    )
}

export default AdminPanel;