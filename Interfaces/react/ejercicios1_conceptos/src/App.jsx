import React from 'react'
import './App.css'
import Welcome from './welcome'
import StyledBox from './StyledBox'
import './Stylebox.css'

import Wrapper from './Wrapper'
import './Wrapper.css'

import Image from './Image'


import Greeting from './Greeting'

import StatusMessage from './StatusMessage'
import ItemList from './ItemList'
import Card from './Card'

function App() {

  const numeroDeItems = 5
  const items = Array.from({ length: numeroDeItems }, (_, i) => `Item ${i + 1}`);

  return (
    <>
    
      <Welcome />
      <StyledBox />
      <Wrapper>
        <p>Este es el contenido dentro del Wrapper.</p>
      </Wrapper>
      <div>
        <Image
          src="https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fimages3.memedroid.com%2Fimages%2FUPLOADED336%2F64494389eecaf.jpeg&f=1&nofb=1&ipt=cd764bcbd636d98699fd39c91fe71ff5e321d803fac8cba61e287992465c181e"
          width="150px"
          alt="150px"
          description="Esta es una imagen de ejemplo"
        />
      </div>
      <div>
        <Greeting name="Juan" />
        <StatusMessage isOnline/>
        <ItemList items={items}/>
        <Card titulo="titulo" children="hola"/>
      </div>

    </>
  )
}

export default App
