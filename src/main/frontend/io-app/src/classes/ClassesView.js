import {useEffect, useState} from "react";
import {Calendar, Views, momentLocalizer} from "react-big-calendar"
import moment from "moment"
import ClassView from "./ClassView";
import {ClassesProvider, useClasses} from "./ClassesContext";

function ClassesView() {
    const userFromLocalStorage = JSON.parse(localStorage.getItem("USER")) ?? null;
    const userId = userFromLocalStorage ? userFromLocalStorage.id : null;

    return (
        <ClassesProvider userId={userId}>
            <ClassesContent />
        </ClassesProvider>
    )
}

function ClassesContent() {
    const { classesEvents } = useClasses();

    // const [classesEvents, setClassesEvents] = useState([]);

    const [currentDate, setCurrentDate] = useState(new Date());
    const [currentView, setCurrentView] = useState(Views.WORK_WEEK);

    const localizer = momentLocalizer(moment);

    return (
        <div className="p-5">
            <h1>Plan zajęć</h1>
            <hr></hr>
            <Calendar
                localizer={localizer}
                events={classesEvents}
                style={{height: 1500}}
                date={currentDate}
                onNavigate={date => setCurrentDate(date)}
                views={[Views.DAY, Views.WORK_WEEK, Views.MONTH]}
                view={currentView}
                onView={view => setCurrentView(view)}
                messages={{today: "Aktualny", next: "Następny", previous: "Poprzedni", work_week: "Widok tygodnia", month: "Widok miesiąca", day: "Widok dnia"}}
                min={new Date(2025, 0, 1, 8, 0)}
                max={new Date(2025, 0, 1, 20, 0)}
                popup={true}
                eventPropGetter={(event) => {
                    return {
                        style: {
                            backgroundColor: 'transparent',
                            padding: 0,
                            border: 'none',
                            height: '100%',
                        }
                    }
                }}
                components={{event: ClassView}}
            />
        </div>
    )
}

export default ClassesView;