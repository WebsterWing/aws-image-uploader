import axios from 'axios'
import React, {useCallback} from 'react'
import {useDropzone} from 'react-dropzone'

export default function UserProfile({uuid, username, userProfileImageLink, updateProfile}) {
  const onDrop = useCallback(acceptedFiles => {
    const file = acceptedFiles[0]
    //axios.post(`http://localhost:8080/api/v1/user-profile/${uuid}/image`)
    const formData = new FormData();
    formData.append("file", file);

    axios.post(
      `/api/v1/user-profile/${uuid}/image`,
      formData,
      {
        headers: {
          "Content-Type": "multipart/form-data"
        }
      }
    ).then(res => {
      console.log("File uploaded sucessfully")
      console.log(res.data)
      updateProfile(res.data)
    }).catch(err => {
      console.log(err)
    })
  }, [uuid, updateProfile])

  const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop})

  const dropzoneClassName = `dropzone ${isDragActive ? 'dropzone-active' : ''}`
  return (
    <div className="userProfile">
      { userProfileImageLink
      // eslint-disable-next-line jsx-a11y/alt-text
      ? <img 
          src={userProfileImageLink}  
        />
      : null }
      {/* TODO: display profile image here */}
      <h1>{username}</h1>
      <div className={dropzoneClassName} {...getRootProps()}>
        <input {...getInputProps()} />
        <p>
        {
          isDragActive ?
            'Drop the files here ...' :
            'Drag \'n drop a profile image here, or click to select one'
        }
        </p>
      </div>
      
      <p>{uuid}</p>
    </div>
  )
}
