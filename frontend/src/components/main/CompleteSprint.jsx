import React, {useContext, useEffect, useState} from "react";
import cmp from "../../assets/cmp-sprint.svg"
import {useNavigate, useParams} from "react-router-dom";
import {toast} from "react-toastify";
import axios from "axios";
import {RefreshContext} from "../../App.jsx";
export const CompleteSprint = () => {
    const [isOpen, setIsOpen] = useState(false);
    const [taskCount, setTaskCount] = useState([]);
    const [locRefresh, setLocRefresh] = useState(0);
    const [isloading, setIsloading] = useState(true);
    const {teamid} = useParams();
    const {refresh,setRefresh} = useContext(RefreshContext);
    const backendApiUrl = import.meta.env.VITE_BACKEND_API_URL;
    useEffect(() => {
        const getTaskCount = async () => {
            setIsloading(()=>true)
            const response = await fetch(`${backendApiUrl}/api/teams/${teamid}/sprint/tasks-count`)
            const jsonData = await response.json();
            setTaskCount(jsonData);
            console.log("TaskCount:",jsonData);
            setIsloading(()=>false)
        };
        getTaskCount();

    }, [locRefresh]);
    const openModal = () => {
        setIsOpen(true);
        setLocRefresh((prevState)=>prevState+1)
    };
    const closeModal = () => {
        setIsOpen(false);
    };

    const handleSubmit = async () => {

        const response = await axios.delete(`${backendApiUrl}/api/teams/${teamid}/sprint/complete`,[]);
        if(response.status==200){
            toast.success("Sprint Completed successfully!");
            setRefresh((prev) => prev+1);
            navigate(`/teams/${teamid}/sprint`);
            closeModal()
        }else {
            toast.error(response.message);
        }
    };
    const navigate = useNavigate();
    return (
        <>
            <button
                className="btn btn-error btn-sm"
                onClick={openModal}
            >
                Complete Sprint
            </button>

            {isOpen && (
                <div className="modal modal-open">
                    <div className="modal-box">
                        <img className="w-full h-[20vh] object-cover" src={cmp} alt="image" />
                        <div>Complete Sprint </div>
                        <div className={"w-full text-left text-lg"}>
                            <div >
                                This sprint contains :

                                    {
                                        isloading ? <div className={"p-2"}>
                                            <div className="skeleton h-4 w-full m-1"></div>
                                            <div className="skeleton h-4 w-full m-1"></div>
                                        </div> :
                                            <ul className={"list-disc px-8"}>
                                                <li>{taskCount.completedTaskCount} completed tasks</li>
                                                <li>{taskCount.remainingTaskCount} open tasks</li>
                                            </ul>
                                    }


                        </div>
                            <br/>
                            <div >
                                Open tasks will be moved to:
                            </div>
                            <div>
                                <input type="text" placeholder="Backlog"
                                       className="input input-bordered w-full max-w-xs" disabled/>
                            </div>
                        </div>
                        <div className="modal-action">
                            <button className="btn btn-primary btn-md" onClick={handleSubmit}>
                                Complete
                            </button>
                            <button className="btn btn-ghost btn-md" onClick={closeModal}>
                                Cancel
                            </button>
                        </div>
                    </div>
                </div>
            )}

        </>
    )
}
