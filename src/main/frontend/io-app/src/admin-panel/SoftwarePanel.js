import {useEffect, useState} from "react";
import Popup from "reactjs-popup";
import {API_URL} from "../config";

function SoftwarePanel() {
    const [softwareName, setSoftwareName] = useState("");
    const [softwares, setSoftwares] = useState([]);
    const [showUsagePopup, setShowUsagePopup] = useState(false);
    const [popupMessage, setPopupMessage] = useState("");

    useEffect(() => {
        fetchSoftwares();
    }, []);

    async function fetchSoftwares() {
        try {
            const response = await fetch(`${API_URL}/software`);
            if (response.ok) {
                const data = await response.json();
                setSoftwares(data);
            } else {
                console.error("Nie udało się pobrać oprogramowania.");
            }
        } catch (error) {
            console.error("Błąd podczas pobierania oprogramowania:", error);
        }
    }

    async function handleAddSoftware() {
        try {
            if (!softwareName.trim()) {
                alert("Podaj nazwę oprogramowania.");
                return;
            }
            const encodedName = encodeURIComponent(softwareName.trim());
            const response = await fetch(`${API_URL}/software/${encodedName}`, {
                method: "POST",
            });
            if (response.ok) {
                await fetchSoftwares();
                setSoftwareName("");
            } else {
                console.error("Nie udało się dodać oprogramowania.");
            }
        } catch (error) {
            console.error("Błąd przy dodawaniu oprogramowania:", error);
        }
    }

    async function isSoftwareUsed(name) {
        try {
            const response = await fetch(`${API_URL}/classrooms/software/${name}`);
            if (!response.ok) {
                console.error("Nie udało się sprawdzić użycia oprogramowania.");
                return false;
            }
            const usageData = await response.json();
            console.log(usageData);
            return usageData && usageData.length > 0;
        } catch (error) {
            console.error("Błąd przy sprawdzaniu użycia oprogramowania:", error);
            return false;
        }
    }

    async function handleDeleteSoftware(id, name) {
        const used = await isSoftwareUsed(name);

        if (used) {
            setPopupMessage("Oprogramowanie jest używane w kursach i nie można go usunąć.");
            setShowUsagePopup(true);
            return;
        }

        if (!window.confirm("Na pewno chcesz usunąć to oprogramowanie?")) return;

        try {
            const response = await fetch(`${API_URL}/software/${id}`, {
                method: "DELETE",
            });
            if (response.ok) {
                await fetchSoftwares();
            } else {
                console.error("Nie udało się usunąć oprogramowania.");
            }
        } catch (error) {
            console.error("Błąd przy usuwaniu oprogramowania:", error);
        }
    }

    return (
        <div className="p-4">
            <div className="row mb-4">
                <div className="col">
                    <h1>Zarządzanie oprogramowaniem</h1>
                </div>
                <div className="col text-end">
                    <Popup trigger={<button className="btn btn-lg btn-primary">Dodaj oprogramowanie</button>} modal
                           nested>
                        {close => (
                            <div className="p-3">
                                <h2>Nowe oprogramowanie</h2>
                                <hr/>
                                <div className="mb-3">
                                    <label><b>Nazwa oprogramowania:</b></label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        value={softwareName}
                                        onChange={(e) => setSoftwareName(e.target.value)}
                                    />
                                </div>
                                <div className="d-flex gap-2">
                                    <button
                                        className="btn btn-success"
                                        onClick={() => {
                                            handleAddSoftware();
                                            close();
                                        }}
                                        disabled={!softwareName.trim()}
                                    >
                                        Dodaj
                                    </button>
                                    <button className="btn btn-secondary" onClick={close}>Anuluj</button>
                                </div>
                            </div>
                        )}
                    </Popup>
                </div>
            </div>

            <hr/>

            <div>
                <h2>Lista oprogramowania</h2>
                {softwares.length === 0 ? (
                    <p>Brak oprogramowania.</p>
                ) : (
                    <table className="table table-striped">
                        <thead>
                        <tr>
                            <th>Nazwa</th>
                            <th>Akcje</th>
                        </tr>
                        </thead>
                        <tbody>
                        {softwares.map(sw => (
                            <tr key={sw.id}>
                                <td>{sw.name}</td>
                                <td>
                                    <button
                                        className="btn btn-danger btn-sm"
                                        onClick={() => handleDeleteSoftware(sw.id, sw.name)}
                                    >
                                        Usuń
                                    </button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                )}
            </div>

            <Popup open={showUsagePopup} closeOnDocumentClick onClose={() => setShowUsagePopup(false)} modal>
                {close => (
                    <div className="p-3">
                        <h3>Uwaga</h3>
                        <hr/>
                        <p>{popupMessage}</p>
                        <div className="text-end">
                            <button className="btn btn-primary" onClick={() => {
                                setShowUsagePopup(false);
                                close();
                            }}>
                                OK
                            </button>
                        </div>
                    </div>
                )}
            </Popup>
        </div>
    );
}

export default SoftwarePanel;
