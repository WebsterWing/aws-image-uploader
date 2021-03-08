import React, {useState, useEffect} from "react";
import axios from "axios";
import UserProfile from "./UserProfile";


export default function UserProfiles() {

  const [userProfiles, setUserProfiles] = useState([])

  useEffect(() => {
    axios.get("/api/v1/user-profile")
      .then(res => {
        console.log(res);
        const {data} = res;
        setUserProfiles(data)
      }).catch(err=> {
        console.log(err.toJSON())
      })
  }, [])

  const updateProfile = newProfile => {
    setUserProfiles(userProfiles.map(oldProfile => {
      if (oldProfile.uuid === newProfile.uuid) {
        return newProfile
      } else {
        return oldProfile
      }
    }))
  }
  
  return (
    <div>
      {userProfiles.map(profile => {
        return <UserProfile key={profile.uuid} updateProfile={updateProfile} {...profile} />
      })}
    </div>
  )
}
