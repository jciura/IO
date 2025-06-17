import { useEffect, useState } from "react";
import RequestCard from "./RequestCard";

function RequestList() {
    const [requests, setRequests] = useState([]);
    const currentUserId = JSON.parse(localStorage.getItem("USER")).id;

    useEffect(() => {
        async function fetchRequests() {
            try {
                const response = await fetch(`http://localhost:8080/reschedule/${currentUserId}`);
                if (response.ok) {
                    const data = await response.json();
                    setRequests(data);
                } else {
                    console.error("Failed to fetch requests");
                }
            } catch (error) {
                console.error("Error fetching requests:", error);
            }
        }

        fetchRequests();
    }, [currentUserId]);

    return (
        <div className="row">
            {requests.length > 0 ? (
                requests.map((request) => (
                    <RequestCard key={request.id} request={request} />
                ))
            ) : (
                <p>Brak dostępnych propozycji zmiany terminu.</p>
            )}
        </div>
    );
}

export default RequestList;
