import {useContext, useEffect, useState} from "react";
import {RefreshContext} from "../../App.jsx";
import {useParams} from "react-router-dom";

export const Backlog = () => {
    const [backlog,setBacklog] = useState([]);
    const {refresh,setRefresh} = useContext(RefreshContext);
    const [isloading, setIsloading] = useState(true)
    const {teamid} = useParams();
    const backendApiUrl = import.meta.env.VITE_BACKEND_API_URL;
    useEffect(() => {
        const getBacklog = async () =>{
            setIsloading(() => true)
            const response = await fetch(`${backendApiUrl}/api/teams/${teamid}/backlog`);
            if(response.ok){
                const jsonData = await response.json();
                setBacklog(jsonData);
                console.log("Backlog:",jsonData.tasks);
            }
            setIsloading(()=> false)
            console.log(backlog.length)
        };
        getBacklog();
    }, [refresh]);

    return (
        <>
            <div className={"text-xl px-2 my-3 pl-2.5"}>
                <span>Backlog</span>
            </div>
            {
                isloading ? <div className="skeleton rounded-lg mx-2 my-1 p-2 h-32"></div> : <div className={"sprint-bg rounded-lg mx-2 my-1 p-2 min-h-32"}>
                        <div className="overflow-x-auto">
                            <table className="table table-zebra">
                                {/* head */}
                                <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>Title</th>
                                    <th>Type</th>
                                    <th>Assigned To</th>
                                    <th>Story Points</th>
                                    <th>Sprint</th>
                                </tr>
                                </thead>
                                <tbody>
                                {
                                    backlog.tasks && backlog.tasks.map(
                                        (b) => (
                                            <tr>
                                                <th>{b.id}</th>
                                                <td>{b.title}</td>
                                                <td>{b.type}</td>
                                                <td>{b.user.name}</td>
                                                <td>{b.storyPoints}</td>
                                                <td>{b.sprint}</td>
                                            </tr>
                                        )
                                    )
                                }

                                </tbody>
                            </table>
                        </div>
                    </div>
            }


        </>
    )
}
