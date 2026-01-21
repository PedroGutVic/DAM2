package com.example.srodenas.interfazelementosentrada

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import com.example.srodenas.interfazelementosentrada.databinding.ActivityMainBinding
import com.example.srodenas.interfazelementosentrada.porsi_no_os_enterais.ClickOnButton
import com.example.srodenas.interfazelementosentrada.porsi_no_os_enterais.ClickOnButtonAbstract
import com.example.srodenas.interfazelementosentrada.porsi_no_os_enterais.SelectElementSpinner
import com.example.srodenas.interfazelementosentrada.porsi_no_os_enterais.SelectElementSpinnerAbstract

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var alumns : MutableList<String>
    private lateinit var adapter : ArrayAdapter<String>

    init {
        alumns = Alumns.namesAlumns.toMutableList()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initEvent()
        initAdapter()
    }


    private fun initAdapter() {
        adapter = ArrayAdapter (
            this,
            R.layout.simple_dropdown_item_1line,
            alumns
        )
        binding.editAuto.setAdapter(adapter)
        binding.spinner.adapter = adapter
    }




    private fun initEvent() {
        proveBtnFloat()
        proveCheck()
        proveCheck()
        proveRadio()
        proveSwitch()
        proveSpinner()
        proveToggle()
        proveButtonActionEditor()
    }

    private fun proveButtonActionEditor() {
       //  Anonima con lambda
        binding.editText.setOnEditorActionListener{
            v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEND){
                showMsg("Clase, capturo el evento del Action del teclado")
                true
            }else
                false
        }


        //Clase anónima
     /*   binding.editText.setOnEditorActionListener(
            object: TextView.OnEditorActionListener{
                override fun onEditorAction(
                    v: TextView?,
                    actionId: Int,
                    event: KeyEvent?
                ): Boolean {
                    if (actionId == EditorInfo.IME_ACTION_SEND){
                        showMsg("Clase, capturo el evento del Action del teclado")
                        return true
                    }else
                        return false
                }
            }
        )

      */
    }


    private fun proveRadio(){
        binding.radioGroup.setOnCheckedChangeListener{
                group, chekedId ->
            when (chekedId){
                binding.btr1.id->{
                    showMsg("Botón radio1, Seleccionado")
                }

                binding.btr2.id->{
                    showMsg("Botón radio2, Seleccionado")

                }
                binding.btr3.id->{
                    showMsg("Botón radio3, Seleccionado")
                }
            }//fin when
        }//fin función lambda
    }



    private fun proveCheck(){
        binding.chk1.setOnCheckedChangeListener{
                btnView, isCheked ->
            if (isCheked){
                showMsg("Checked1, seleccionado")
            }else{
                showMsg("Checked1, Deseleccionado")
            }
        }

        binding.chk2.setOnCheckedChangeListener{
                btnView, isCheked ->
            if (isCheked){
                showMsg("Checked2, seleccionado")
            }else{
                showMsg("Checked2, Deseleccionado")
            }
        }

        binding.chk3.setOnCheckedChangeListener{
                btnView, isCheked ->
            if (isCheked){
                showMsg("Checked3, seleccionado")
            }else{
                showMsg("Checked3, Deseleccionado")
            }
        }
    }





    private fun proveSwitch(){
        binding.switchBtn.setOnCheckedChangeListener {
                buttonView, isChecked ->
            if (isChecked){
                showMsg("Boton Switch activado")
            }else{
                showMsg("Boton Switch Desactivado")
            }
        }
    }





    private fun proveSpinner(){
        // val myEvent : ClickOnButtonAbstract = ClickOnButtonAbstract(this)
        /*
        Este es el codigo correcto. Clase anónima, porque sobreescribo el código
        directamente.
         */
         /*  binding.spinner.onItemSelectedListener =
               object :  AdapterView.OnItemSelectedListener {
                   override fun onItemSelected(
                       parent: AdapterView<*>?,
                       view: View?,
                       position: Int,
                       id: Long
                   ) {
                       showMsg(parent?.getItemAtPosition(position).toString())              }

                   override fun onNothingSelected(parent: AdapterView<*>?) {
                       showMsg("No selecciono nada")
                   }
               }
*/
        /*
        Lo véis como si defino una clase con el código en sus métodos sobreescritos
        sólo tengo que crearmelo. El problema es, y si tengo que crearme 20 objetos
        con comportamiento diferente? Tengo que crearme 20 clases del tipo SelectElementSpinner ??
        Para esto tengo las clases anónimas.
         */
        //  binding.spinner.onItemSelectedListener = SelectElementSpinner(this)

        /*
        Con clase Abstracta y sobreescribiendo los métodos abstractos de la interfaz
        De todas formas, tengo que crear objeto de la clase Abstracta, ya que tengo que
        implementar sus métodos.
         */
        binding.spinner.onItemSelectedListener =
            object : SelectElementSpinnerAbstract(this){
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val select = parent?.getItemAtPosition(position).toString()
                    if (select != "Selecciona uno")
                        showMsg(parent?.getItemAtPosition(position).toString()+"--")
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    showMsg("No selecciono nada")
                }
            }
    }






    private fun proveToggle(){
        binding.toggBtn.setOnCheckedChangeListener {
                buttonView, isChecked ->
            if (isChecked){
                showMsg("Boton Toggle activado")
            }else{
                showMsg("Boton Toggle Desactivado")
            }
        }
    }





    private fun proveBtnFloat(){
        /* ****PONEMOS EL BOTÓN A LA ESPERA DE LA ACTIVACIÓN DEL MÉTODO OnClick*****


      El botón llama al método setOnClickListener pasando como referencia una función
      del tipo View -> Unit, del cual defino su código aquí.
      El método setOnClickListener podría definirse dentro de la API como:


                 fun  setOnClickListener(onClick: (View v)-> Unit){
                          //bla bla bla bla
                          onClick(botonQueGeneraElEvento)
                 }

        */
        binding.btnFloat.setOnClickListener{
            showMsg(binding.editText.text.toString())
        }




        /*
        Ejemplo utilizando un objeto del tipo ClickOnButton. Este objeto
        es del tipo ClickOnButton que implementa de la interfaz y define
        su lógica en el método onClick(View v)
         */
        //   binding.btnFloat.setOnClickListener(ClickOnButton(this))
        /*



       Ejemplo de como implementar el listener con clase anónima
        */
        val event : View.OnClickListener
        event = object : View.OnClickListener{
            override fun onClick(v: View?) {
                showMsg("Click desde objeto anónimo del botón")            }
        }

        /*
        botón, espera a recibir un objeto event.
        Cuando lo recibas, llama implícitamente al método onClick del objeto event.
         */
        binding.btnFloat.setOnClickListener(event)


        /*
        Ejemplo, utilizando nuestra clase abstracta y un objeto anónimo.
         */
        /*   binding.btnFloat.setOnClickListener(object: ClickOnButtonAbstract(this){
               override fun onClick(v: View?) {
                   showMsg("Click desde objeto anónimo del botón")
               }
           })

         */

    }

    private fun showMsg(cad : String){
        Toast.makeText(this, cad, Toast.LENGTH_LONG).show()
    }


}


