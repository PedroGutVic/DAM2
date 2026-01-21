import { useState } from 'react';
import './App.css'



function App() {
  


  const [contador , setContador] = useState(0)



  return (
    <>
      <p>
        El contador vale :{contador}
      </p>
      <button onClick={()=>{
        setContador(contador+1)
        console.log(contador)
      }}> Incrementar</button>
    </>
  )
}

export default App
