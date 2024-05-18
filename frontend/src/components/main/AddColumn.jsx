
import React, { useState } from 'react';
import axios from "axios";
import {toast} from "react-toastify";
import {useParams} from "react-router-dom";

const AddColumn = ({setRefresh}) => {
    const [showInput, setShowInput] = useState(false);
    const [inputValue, setInputValue] = useState('');

    const {teamid} = useParams();
    const handleInputChange = (e) => {
        setInputValue(e.target.value);
    };
    const backendApiUrl = import.meta.env.VITE_BACKEND_API_URL;
    const handleSaveInput = async () => {

        console.log('Input value:', inputValue);
        try{
            if(inputValue.length!=0){
                const response = await axios.post(`${backendApiUrl}/api/teams/${teamid}/sprint/columns`,{
                    title:inputValue
                });
                if (response.status==201){
                    toast.success("Column added successfully")
                    setRefresh(prev => prev+1);
                }else{
                    throw Error("Error -" +response.status);
                }
            }
            setShowInput(false);
            setInputValue('');

        }catch (error){
            toast.error(error.message);
        }

    };

    return (
        <div className="pt-1">
            {showInput ? (
                <div className="flex items-center">
                    <input
                        type="text"
                        value={inputValue}
                        onChange={handleInputChange}
                        className="border border-gray-300 rounded-l-md px-3 py-2 mr-2"
                        placeholder="Enter text"
                    />

                    <button onClick={handleSaveInput} className="btn btn-outline">✓</button>
                    <button onClick={() => setShowInput(false)} className="btn btn-outline">X</button>
                </div>
            ) : (
                <button
                    onClick={() => setShowInput(true)}
                    className="bg-gray-200 hover:bg-neutral-content text-black rounded-md px-4 py-2"
                >
                    +
                </button>

            )}
        </div>
    );
};

export default AddColumn;