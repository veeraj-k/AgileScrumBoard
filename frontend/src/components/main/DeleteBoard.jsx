import {useEffect} from "react";
import axios from "axios";
import {toast} from "react-toastify";
import { FaTrashCan } from "react-icons/fa6";
export const DeleteBoard = ({boardid,setRefresh}) => {
    const backendApiUrl = import.meta.env.VITE_BACKEND_API_URL;
    const deleteBoard = async () => {
        try {
            console.log("hello biuatc");
            const response = await axios.delete(`${backendApiUrl}/api/boards/${boardid}`);
            if (response.status === 200) {
                toast.success("Board deleted successfully!");
                setRefresh((prev) => prev + 1);
            } else {
                throw new Error();
            }
        } catch (error) {
            toast.error("Error deleting the board!");
        }
    };


    return (


        <div

        >

            <button className="btn btn-xs btn-error" onClick={deleteBoard}>Complete Sprint</button>
        </div>
    )
}
