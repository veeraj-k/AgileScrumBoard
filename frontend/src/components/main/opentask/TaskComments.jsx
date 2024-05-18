import {useContext, useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import axios from "axios";
import {toast} from "react-toastify";
import {RefreshContext} from "../../../App.jsx";



export const TaskComments = () => {
    const [comments,setComments] = useState([]);

    const [newComments,setNewComments] = useState("");

    const {refresh,setRefresh} = useContext(RefreshContext);
    const {teamid} = useParams();
    const {taskid} = useParams();
    const backendApiUrl = import.meta.env.VITE_BACKEND_API_URL;

    useEffect(() => {
        const getComments = async () => {
            const response = (await fetch(`${backendApiUrl}/api/teams/${teamid}/sprint/columns/tasks/${taskid}/comments`));
            const jsonData = await response.json();
            setComments(jsonData);
            console.log("Comments :" ,jsonData);
        }
        getComments()
    }, [refresh]);

    const handleOnCommentChange = (e) => {
        setNewComments(e.target.value);
        console.log(newComments)
    }
    const handleNewCommentSubmit = async () => {
        try{
            const response = await axios.post(`${backendApiUrl}/api/teams/${teamid}/sprint/columns/tasks/${taskid}/comments`,{
                description : newComments
            });
            if(response.status == 201){
                toast.success("Comment Added!")
                setNewComments("");
                setRefresh(prev => prev+1);

            }
            else {
                throw Error(response.statusText);
            }
        }
        catch (error){
            toast.error(error.message);
        }
    }

    const handleDeleteComment = async (id) => {
        try{
            const response = await axios.delete(`${backendApiUrl}/api/teams/${teamid}/sprint/columns/tasks/comments/${id}`,{});
            if(response.status == 200){
                toast.success("Comment Deleted!")
                setNewComments("");
                setRefresh(prev => prev+1);

            }
            else {
                throw Error(response.statusText);
            }
        }
        catch (error){
            toast.error(error.message);
        }
    }
    return (
        <>
            <div className="mb-8">
                <h2 className="font-semibold text-lg mb-2">Activity</h2>
                <div className="flex space-x-4 mb-2">
                    <button className="text-blue-500 border-b border-blue-500">Comments</button>
                </div>
                <div className="space-y-4">

                    <div className="flex items-center space-x-2">
                        <div
                            className="bg-blue-700 text-white rounded-full w-8 h-8 flex items-center justify-center">V
                        </div>
                        <input
                            className="border rounded-md w-full p-2"
                            placeholder="Add a comment..."
                            type="text"
                            onKeyDown={(event) => {
                                if (event.keyCode === 13) {
                                    console.log('You pressed Enter!');
                                    handleNewCommentSubmit()
                                }
                            }}
                            value={newComments}
                            onChange={handleOnCommentChange}
                        />
                    </div>
                    {/*comments*/}
                    {
                        comments.map((comment) => (

                            <div className="flex items-start space-x-2  p-2" key={comment.id}>
                                <div
                                    className="bg-blue-500 text-white rounded-full w-8 h-8 flex items-center justify-center">{comment.user.name.charAt(0)}
                                </div>
                                <div>
                                    <p className="text-sm base-content">{comment.user.name} April 14, 2024 at 7:39 PM</p>
                                    <p className={"text-lg"}>{comment.description}</p>
                                    <button className="text-sm text-blue-500" onClick={() => handleDeleteComment(comment.id)}>Delete</button>
                                </div>
                            </div>
                        ))
                    }

                </div>
            </div>
        </>
    )
}
