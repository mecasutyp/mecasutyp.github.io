//Actualización 2016: Salvador Zamora Arias
function cargarArchivos(vista, div, mensaje,li){ 
    //Se evalua la pestaña y determinar que tipo de cargado se efectuara
    if($("li[id='"+li+"']").hasClass("ui-state-active")==false){
        //alert("carga el primero li no active");
        //Se carga el primer acordeon
        cargarContenido(vista, div, mensaje);   
    }else{
        //Se carga el contenido de los acordeonesdentro de la misma pestaña.
        //alert("li active");
        if($("div[id='"+div+"']").hasClass("ui-accordion-content-active")==false){           
            cargarContenido(vista, div, mensaje);
        }            
    }
}
//Muestra el cntenido en los divs Correspondientes de acuerdo al contenido deseado
function cargarContenido(vista, div, mensaje){
    var url = "/Vistas/Cgut/"+vista+".jsp";
    modal("Espere...","<img alt='MECASUT' src='images/ajax-loader.gif' /><br/> Cargando "+mensaje+"...");
    setTimeout(function(){
        //$("div[id^=DivContenido]").html("");           
        $("#"+div).load(url,function(responseTxt,statusTxt,xhr){
            if(statusTxt=="success"){
                $("#dialog-modal").dialog("close");
                if(vista=="CgutMatriculaTotal"){
                    mat_Total(0,0,0);
                }
            }
            if(statusTxt=="error"){
                alert("Error: "+xhr.status+": "+xhr.statusText);
            }
        });
    },400);        
}

//cambiado
function solo_numeros(control,id_uni,uni,niv){
    if(!/^([0-9])*$/.test(control.value)){
        $().alerta('amarillo', 'Este valor ' + "'" + control.value + "'" + ' no es v&aacute;lido');
        $(control).globo("enable").globo("update", "<center>Por favor<br/>solo numeros<center>").globo("show");
        $(control).removeClass("inputok").addClass("inputerror");
    }else{
        $(control).addClass("inputok").removeClass("inputerror");
        $(control).globo("disable");
        $(control).val(Number(control.value));
        mat_Total(0,id_uni,uni,niv);      
        if(!$("form[name='CgutMatriculaTotalForm'] input").hasClass("inputerror")){
            mat_Total(0,id_uni,uni,niv);      
        }
    }
}

function noControles(punto){
    var i = 0;
    while(document.forms["CgutEgresadosForm"][i].name != punto){
        i++;
    }
    return i;
}

//agregado
function noControlesN(punto){
    var i = 0;
    while(document.forms["CgutNuevoIngresoForm"][i].name != punto){
        i++;
    }
    return i;
}

//agregado
function noControlesM(punto){
    var i = 0;
    while(document.forms["CgutMatriculaTotalForm"][i].name != punto){
        i++;
    }
    return i;
}
//Funciones de Egresados

//cambiado
function valoresEgresados(){
    var cadena = "";
    for(i = noControles("inicioEgresados")+1;i < noControles("finEgresados");i ++){
        cadena += document.forms["CgutEgresadosForm"][i+1].value + "," + document.forms["CgutEgresadosForm"][i].value + "-";
        i += 1;
    }
    return cadena;
}

//cambiado
function validar_egresados(){
    if($("form[name='CgutEgresadosForm'] input").hasClass("inputerror")){
        $().alerta('rojo','Algunos datos en el formulario de Egresados de Bachillerato no son v&aacute;lidos.');
    }else{
        $( "#dialog-procesando" ).dialog({
            height: 180,
            modal: true,
            show: "fade",
            hide: "fade"
        }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
        retrieveURL("/CgutEgresadosAction.msut?egresados_bachillerato="+valoresEgresados(),'CgutEgresadosForm');
        $().alerta('verde','Los datos se han guardado correctamente.');
    }
}

//cambiado
//Funciones de Nuevo Ingreso
function valoresNuevoIng(){
    var cadena = "";
    for(i = noControlesN("inicioNuevoIng")+1;i < noControlesN("finNuevoIng");i ++){
        cadena += document.forms["CgutNuevoIngresoForm"][i+1].value + "," + document.forms["CgutNuevoIngresoForm"][i].value + "-";
        i += 1;
    }
    return cadena;
}
//cambiado
function validar_nuevo(){
    if($("form[name='CgutNuevoIngresoForm'] input").hasClass("inputerror")){
        $().alerta('rojo','Algunos datos en el formulario de Alumnos de Nuevo Ingreso no son v&aacute;lidos.');
    }else{    
        $( "#dialog-procesando" ).dialog({
            height: 180,
            modal: true,
            show: "fade",
            hide: "fade"
        }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
        retrieveURL("/CgutNuevoIngresoAction.msut?nuevo_ingreso="+valoresNuevoIng(),'CgutNuevoIngresoForm');
        $().alerta('verde','Los datos se han guardado correctamente.');
    }
}

//cambiado
//Funciones de Matricula Total
function valoresMatTotal(niveles){
    var cadena = "";
    for(i = noControlesM("inicioMatTotal")+1;i < noControlesM("finMatTotal");i ++){
        cadena += document.forms["CgutInterfaceForm"][i+niveles].value;
        for(j = 0; j < niveles; j++){
            cadena += "," + document.forms["CgutMatriculaTotalForm"][i+j].value;
        }
        cadena += "-";
        i += niveles;
    }
    return cadena;
}
//cambiado
function validar_mat(niveles){
    if($("form[name='CgutMatriculaTotalForm'] input").hasClass("inputerror")){
        $().alerta('rojo','Algunos datos en el formulario de Alumnos de Nuevo Ingreso no son v&aacute;lidos.');
    }else{
        $( "#dialog-procesando" ).dialog({
            height: 180,
            modal: true,
            show: "fade",
            hide: "fade"
        }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
        retrieveURL("/CgutMatriculaTotalAction.msut?mat_total="+valoresMatTotal(niveles),'CgutMatriculaTotalForm');
        $().alerta('verde','Los datos se han guardado correctamente.');

    }
}
//otras funciones
function validarFormularioCausas(){
    if(document.getElementById("nombreCausas").value==""){
        $().alerta('rojo','No se han podido guardar los datos de Causas de Deserci&oacute;n.');
        $().alerta('amarillo',"Por favor escriba la causa<br/>de deserci&oacute;n");
    }else{
        retrieveURL('/CgutCausas.msut?ask=guardarCausa','CgutCausasForm');
        $().alerta('verde','Los datos se han guardado correctamente.');
    }
}

function validarMantenimiento(){    
    var activo, periodo, accion = 0;
    if($("label[for='radio1']").hasClass("ui-state-active")){
        activo = 1;
    }else{
        activo = 0;
    }
    periodo = $("#cboPeriodos").val();

    $(function(){
        $( "#con_CamDatGene" ).dialog({
            // autoOpen: false,        
            width: 450,
            height: 200,
            modal: true,
            buttons: {
                "Aceptar": function() {                       
                    retrieveURL('/CgutMantenimientoMecasutAction.msut?ask=mantMecas&estadoActual='+activo+'&perActual='+periodo,'CgutMantenimientoMecasutForm');
                    $( this ).dialog( "close" );                    
                    //                    setTimeout(function (){                                            
                    //                        //   alert(i);
                    //                        for(var i=1; i<=5; i++){//                       
                    //                            modal("Espere...","<img alt='MECASUT' src='images/ajax-loader.gif' /><br/> Cargando ...");                    
                    //                            if(i==5){
                    //                            }
                    //                                                   }
                    //                    },1000);
                    setTimeout(function (){
                        location.reload();
                                            
                    },5000);
                },
                Cancel: function() {
                    $( this ).dialog( "close" );
                }
            }
        });
    });
//    if(activo != $("[name='estadoActual']").val()){
//        if(!confirm("Se va a modificar el estado activo del MECASUT, ¿Desea continuar?")) {
//            return;
//        }
//        accion++;
//    }
//    if(periodo != $("[name='perActual']").val()){
//        if(!confirm("Se va a modificar el periodo actual de evaluacion del MECASUT, es una accion peligrosa si aun se estan efectuando cambios en la evaluaci&oacute;n, ¿Desea continuar? <br/>La p&aacute;gina se recargar&aacute; autom&aacuteticamente")) {
//            periodo = $("[name='perActual']").val();
//            $("#cboPeriodos").val(periodo);
//            return;
//        }
//        accion++;
//    }
//    if(accion > 0){        
//        retrieveURL('/CgutMantenimientoMecasutAction.msut?ask=mantMecas&estadoActual='+activo+'&perActual='+periodo,'CgutMantenimientoMecasutForm');
//    }
}
function desplegarDiv(){
    if ($("input[id='radio1']").is(':checked')){        
        $("#mant").slideDown();
    }else{
        $("#mant").slideUp();
    }    
}

function reiniciarDatos(){
    if($("[name='uniM']").val() != -1){
        if(confirm("Seguro que desea reiniciar los datos generales del periodo actual de la "+$("[name='uniM'] option[selected]").html()+"? En caso de que se haya iniciado la evaluacion, al borrarlos tambien se tendran que volver llenar los siguentes indicadores: Eficacia: 2,3,4,5,6,7,8,9 y 10, Eficiencia: 15, Pertinencia: 18,19 y 22, Vinculacion: 29 y Equidad: 34. La accion no se puede deshacer.")) {
            retrieveURL('/CgutMantenimientoUniversidadAction.msut?ask=reiniciarDatos&ut='+$("[name='uniM']").val(),'CgutMantenimientoUniversidadForm');
        }
    }
}

function reactivarEvaluacion(){
    if($("[name='uniM']").val() != -1){
        if ($("#terminado").val()=="true"){
            if (confirm("Los datos de los indicadores no se modifican ni  se eliminan. Seguro que desea reactivar la evalacion de la UT? ")){
                retrieveURL('/CgutMantenimientoUniversidadAction.msut?ask=reactivarEvaluacion','CgutMantenimientoUniversidadForm');
                $().alerta('verde','Se ha reactivado la evaluaci&oacute;n para la UT');
            }
        }else{
            $().alerta('amarillo','Alerta. <br/>La universidad seleccionada no ha terminado la evaluaci&oacute;n');
        }
    }else{
        $().alerta('amarillo','Alerta. <br/>Seleccione una UT');
    }
}


function reiniciarCategoria(){
    if($("[name='uniM']").val() != -1 && $("[name='cboReiniciar']").val() != -1){ //si el combo combo uniM es diferente del valor inicial                                                                               
        if(confirm("Seguro que desea reiniciar "+$("[name='cboReiniciar'] option[value='"+ $('[name="cboReiniciar"]').val() +"']").html()+" del periodo actual de la "+$("[name='uniM'] option[selected]").html()+"? esta acci&oacute;n no se puede deshacer.")) {
            retrieveURL('/CgutMantenimientoUniversidadAction.msut?ask=reiniciarCat&cat='+$("[name='cboReiniciar']").val()+'&ut='+$("[name='uniM']").val(),'CgutMantenimientoUniversidadForm');
        }else{ //El combo reiniciar estÃ¡ en el valor inicial
            $().alerta('rojo','Por favor, seleccione una categor&iacute;a');
        }
    }else{//el combo universidad estÃ¡ en el valor inicial
        $().alerta('rojo','Por favor, seleccione una universidad');
    }
}


function bajaCausas(){
    retrieveURL('/CgutCausas.msut?ask=bajarCausa','CgutCausasForm');
    $().alerta('verde','La causa ha cambiado de estado.');
}

function desactivarCombosEncuestas(){
    if ($("#cboEncuestas").val()==-1){//activar campos
        alert("activar campos");
        $('#cboEncuestaIndicador').prop('disabled', false);
        $('#cboNivelEncuesta').prop('disabled', false);
    }else{
        alert("desactivar campos");
        $('#cboEncuestaIndicador').prop('disabled', true);
        $('#cboNivelEncuesta').prop('disabled', true);
    }
}

function validarFormularioEncuestas(){
    //nivel 
    if (document.getElementById("cboNivelEncuesta").value==-1){
        $().alerta('rojo','No se han podido guardar los datos de la Encuesta.');
        $().alerta('amarillo',"Por favor seleccione un nivel");
        return
    }else if (document.getElementById("cboEncuestaIndicador").value=="-1"){
        $().alerta('rojo','No se han podido guardar los datos de la Encuesta.');
        $().alerta('amarillo',"Por favor, seleccione un nivel");
        return;
    }else if(document.getElementById("nombreEncuestas").value==""){
        $().alerta('rojo','No se han podido guardar los datos de la Encuesta.');
        $().alerta('amarillo',"Por favor escriba el nombre<br/>de la Encuesta");
        return;
    }else{
        retrieveURL('/CgutEncuestas.msut?ask=guardarEncuesta','CgutEncuestasForm');
        $().alerta('verde','Los datos se han guardado correctamente.');
    }
}

function validarFormularioPreguntas(){
    var ban = false;
    if (document.getElementById("cmbPregEnc").value>=1){ //combo de encuesta
        if(document.getElementById("cmbPregEnc").value==3){ //combo de encuesta
            //con categorias
            if(document.getElementById("cboCategoPre2").value<0){ //combo de categorias
                $().alerta('rojo','Datos no guardados. <br/> Seleccione una categor&iacute;a');
                $().alerta('amarillo',"Por favor seleccione<br/>una Categor&iacute;a");
            }else{ //todo estÃ¡ correcto
                ban=true;
            }

        }else{
            ban=true;
        }        
        if(document.getElementById("txtNombrePreguntas").value==""){ //combo de categorias
            $().alerta('rojo','Datos no guardados. <br/> Ingrese un nombre de pregunta');
            //  $().alerta('amarillo',"Por favor seleccione<br/>una Categor&iacute;a");
            ban=false;
        }else{ //todo estÃ¡ correcto
            ban=true;
        }
        
    }else{
        $().alerta('rojo','Datos no guardados. <br/> Seleccione una encuesta');
        $().alerta('amarillo',"Por favor seleccione<br/>una encuesta");
    }    
    if (ban==true){ //datos correctos
        retrieveURL('/CgutPreguntas.msut?ask=guardarPregunta','CgutPreguntasForm');
        $().alerta('verde','Los datos se han guardado correctamente.');
    }
}

function bajaCategoriaPregunta(){
    retrieveURL('/CgutInterfaceAction.msut?ask=bajaCategoriaPre','CgutInterfaceForm');
    $().alerta('verde','La categor&iacute;a ha cambiado de estado.');
}
function bajaEncuestaPregunta(){
    retrieveURL('/CgutEncuestas.msut?ask=bajaEncuestasPre','CgutEncuestasForm');
    $().alerta('verde','La encuesta ha cambiado de estado.');
}

function bajaPreguntas(){
    retrieveURL('/CgutPreguntas.msut?ask=bajaPreguntas','CgutPreguntasForm');
    $().alerta('verde','La pregunta ha cambiado de estado.');
}

function validarFormularioCategorias(){
    if(document.getElementById("nombreCategoPregu").value==""){
        $().alerta('rojo','No se han podido guardar los datos de la Categoria.');
        $().alerta('amarillo','Por favor escriba una<br/>nueva Categoria');
    }else if(document.getElementById("nombreCategoDesc").value==""){
        $().alerta('rojo','No se han podido guardar los datos de la Categoria.');
        $().alerta('amarillo','Por favor escriba la descripci&oacute;n<br/>de la Categoria');
    }else{
        retrieveURL('/CgutInterfaceAction.msut?ask=guardarCategoPreg','CgutInterfaceForm');
        $().alerta('verde','Los datos se han guardado correctamente.');
    }
}
function validarFormularioModelo(){
    if(document.getElementById("nombreModelo").value==""){
        $().alerta('rojo','No se han podido guardar los datos del Modelo.');
        $().alerta('amarillo','Por favor escriba<br/>el Modelo');
    }else{
        retrieveURL('/CgutModelos.msut?ask=guardarModelo','CgutModelosForm');
        $().alerta('verde','Los datos se han guardado correctamente.');
    }
}
function bajaModelo(){
    retrieveURL('/CgutModelos.msut?ask=bajaModelo','CgutModelosForm');
    $().alerta('verde','El modelo ha cambiado de estado.');
}
function validarFormularioCertificacion(){
    if(document.getElementById("cboCertificacion").value<0){
        $().alerta('rojo','No se han podido guardar los datos de la certificacion.');
        $().alerta('amarillo','Selecciona una Certificaci&oacute;n');        
    } 
    else if(document.getElementById("nombreCertificacion").value==""){
        $().alerta('rojo','No se han podido guardar los datos de la certificacion.');
        $().alerta('amarillo','>Por favor escriba<br/>el nombre de la Certificacion');
    }else{
        retrieveURL('/CgutCertificacionesAction.msut?ask=guardarCertificacion','CgutCertificacionesForm');
        $().alerta('verde','Los datos se han guardado correctamente.');
    }
}
function validarFormularioEdificio(){
    if(document.getElementById("nombreEdificio").value==""){
        $().alerta('rojo','No se han podido guardar los datos del edificio, debe ingresar un nombre.');
        $().alerta('amarillo','Por favor escriba<br/>el nombre del Edificio');
    }else if(!/^([0-9])*$/.test(document.getElementById("capacidadEdificio").value) || document.getElementById("capacidadEdificio").value==""){
        $().alerta('rojo','No se han podido guardar los datos del edificio, debe ingresar la capacidad con solo n&uacute;meros.');
        $().alerta('amarillo','Por favor ingrese una cantidad<br/>v&aacute;lida');
        return;
    }else{
        retrieveURL('/CgutEdificios.msut?ask=guardarEdificio','CgutEdificiosForm');
        $().alerta('verde','Los datos se han guardado correctamente.');
    }
}
//wero
function bajaCertificacion(){
    retrieveURL('/CgutCertificacionesAction.msut?ask=bajaCertificacion','CgutCertificacionesForm');
    $().alerta('verde','La certificacion ha cambiado de estado.');
}
function bajaEdificio(){
    retrieveURL('/CgutEdificios.msut?ask=bajaEdificio','CgutEdificiosForm');
    $().alerta('verde','El tipo de edificio ha cambiado de estado.');
}

function validarFormularioNiveles(){
    if(document.getElementById("nombreNivel").value==""){
        $().alerta('rojo','No se han podido guardar los datos de los Niveles.');
        $().alerta('amarillo','Por favor escriba<br/>el Nivel');
    }else if(document.getElementById("nombreDescripcion").value==""){
        $().alerta('rojo','No se han podido guardar los datos de los Niveles.');
        $().alerta('amarillo','Por favor escriba<br/>la Descripci&oacute;n del Nivel');
    }else if(document.getElementById("nombreAbreviatura").value==""){
        $().alerta('rojo','No se han podido guardar los datos de los Niveles.');
        $().alerta('amarillo','Por favor escriba<br/>la Abreviatura');
    }else{
        retrieveURL('/CgutNiveles.msut?ask=guardarNivel','CgutNivelesForm');
        $().alerta('verde','Los datos se han guardado correctamente.');
    }
}
function bajaNivel(){
    retrieveURL('/CgutNiveles.msut?ask=bajaNiveles','CgutNivelesForm');
    $().alerta('verde','El nivel ha cambiado de estado.');
}
//wero
function validarFormularioResponsables(){
    if(document.getElementById("cboUni").value == -1){ //combo universidad 
        $().alerta('rojo','No se han podido guardar los datos del Responsable.<br/><center>Por favor seleccione una Universidad<center>');
        $().alerta('amarillo','Por favor seleccione<br/>una Universidad');
        return;
    }else if(document.getElementById("nombreResponsable").value==""){
        $().alerta('rojo','No se han podido guardar los datos del Responsable. <br/> Ingrese un nombre');
        $().alerta('amarillo','Por favor escriba el nombre<br/>del Responsable');
        return;
    }else if(document.getElementById("nombreAPResponsable").value==""){
        $().alerta('rojo','No se han podido guardar los datos del Responsable. <br/> Ingrese el apellido paterno');
        $().alerta('amarillo','Por favor escriba apellido paterno<br/>del Responsable');
        return;
    }else if(document.getElementById("nombreAMResponsable").value==""){
        $().alerta('rojo','No se han podido guardar los datos del Responsable.');
        $().alerta('amarillo','Por favor escriba apellido materno<br/>del Responsable');
        return;
    }else if(document.getElementById("nombreCargo").value==""){
        $().alerta('rojo','No se han podido guardar los datos del Responsable.');
        $().alerta('amarillo','Por favor escriba el cargo<br/>del Responsable');
        return;
    }else if(document.getElementById("nombreTelefono").value==""){
        $().alerta('rojo','No se han podido guardar los datos del Responsable.');
        $().alerta('amarillo','Por favor escriba el tel&eacute;fono<br/>del Responsable');
        return;
    }else if(!/^([0-9])*$/.test(document.getElementById("nombreTelefono").value)){
        $().alerta('rojo','No se han podido guardar los datos del Responsable.');
        $().alerta('amarillo','Por favor escriba un tel&eacute;fono<br/>v&aacute;lido');
        return;
    }else if(document.getElementById("nombreMail").value==""){
        $().alerta('rojo','No se han podido guardar los datos del Responsable.');
        $().alerta('amarillo','Por favor<br/>escriba el correo electr&oacute;nico<br/>del Responsable');
        return;
    }else if(!/[\w-\.]{3,}@([\w-]{2,}\.)*([\w-]{2,}\.)[\w-]{2,4}/.test(document.getElementById("nombreMail").value)){
        $().alerta('rojo','No se han podido guardar los datos del Responsable.');
        $().alerta('amarillo','Por favor revise el correo electr&oacute;nico<br/>del Responsable');
        return;
    }else{
        retrieveURL('/CgutResponsablesAction.msut?ask=guardarResponsable','CgutResponsablesForm');
        $().alerta('verde','Los datos se han guardado correctamente.');
    }
}

function bajaResponsables(){
    retrieveURL('/CgutResponsablesAction.msut?ask=bajaResponsables','CgutResponsablesForm');
    $().alerta('verde','El Responsable ha cambiado de estado.');
}
//wero

function validarFormularioUsuarios(){
    
    
    
    var usuario = $("#nombreUsu").val();
    var pass = $("#password").val();
    var pass2 = $("#confirmacion").val();
    
    if (usuario==""){
        $().alerta('rojo','No se han podido guardar los datos del Usuario. Ingrese un Usuario');
    }else if (pass==""){
        $().alerta('rojo','No se han podido guardar los datos del Usuario. Ingrese una Contrase&ntilde;a');
    }else if (pass2==""){
        $().alerta('rojo','No se han podido guardar los datos del Usuario. Ingrese la confirmaci&oacute;n de la contrase&ntilde;a');
    }else if(pass!=pass2){
        $().alerta('rojo','No se han podido guardar los datos del Usuario. Las contrase&ntilde;as NO coinciden');
    }else{
        //datos correctos, se realiza guardado de usuarios de las universidades
        var disponible = true;
        $("#comboListaUniversidades option").each(function(){
            if ($(this).text()==usuario){
                disponible = false;
            }
        });
        if (disponible){
            retrieveURL('/CgutUsuarios.msut?ask=guardarUsuario','CgutUsuariosForm');
            $().alerta('verde','Los datos se han guardado correctamente.');
        }else{
            $().alerta('amarillo','El nombre de Usuario '+usuario+' ya esta en uso, ingrese otro nombre de usuario por favor ');
        }
        
    /*
         retrieveURL('/CgutUsuarios.msut?ask=guardarUsuario','CgutUsuariosForm');
        $().alerta('verde','Los datos se han guardado correctamente.');
         */
    }
//    var universidad = $("#cboUniUsuarios").val();
//    var usuario = $("#nombreUsu").val();
//    var pass = $("#password").val();
//    var pass2 = $("#confirmacion").val();
//    if (universidad<0){
//        $().alerta('rojo','No se han podido guardar los datos del Usuario. Seleccione una universidad');
//    }else if (universidad==0){
//        //validación para usuarios de la CGUT
//        if (usuario==""){
//            $().alerta('rojo','No se han podido guardar los datos del Usuario. Ingrese un Usuario');
//        }else if (pass==""){
//            $().alerta('rojo','No se han podido guardar los datos del Usuario. Ingrese una Contrase&ntilde;a');
//        }else if (pass2==""){
//            $().alerta('rojo','No se han podido guardar los datos del Usuario. Ingrese la confirmaci&oacute;n de la contrase&ntilde;a');
//        }else if(pass!=pass2){
//            $().alerta('rojo','No se han podido guardar los datos del Usuario. Las contrase&ntilde;as NO coinciden');
//        }else{
//            //datos correctos, se realiza guardado de usuarios de las universidades
//            retrieveURL('/CgutUsuarios.msut?ask=guardarUsuario','CgutUsuariosForm');
//            $().alerta('verde','Los datos se han guardado correctamente.');
//        }
//    }else{
//        //validaciones para los usuarios de las universidades
//        if (usuario==""){
//            $().alerta('rojo','No se han podido guardar los datos del Usuario. Ingrese un Usuario');
//        }else if (pass==""){
//            $().alerta('rojo','No se han podido guardar los datos del Usuario. Ingrese una Contrase&ntilde;a');
//        }else if (pass2==""){
//            $().alerta('rojo','No se han podido guardar los datos del Usuario. Ingrese la confirmaci&oacute;n de la contrase&ntilde;a');
//        }else if(pass!=pass2){
//            $().alerta('rojo','No se han podido guardar los datos del Usuario. Las contrase&ntilde;as NO coinciden');
//        }else{
//            //datos correctos, se realiza guardado de usuarios de las universidades
//            retrieveURL('/CgutUsuarios.msut?ask=guardarUsuario','CgutUsuariosForm');
//            $().alerta('verde','Los datos se han guardado correctamente.');
//        }
//    }
}

function bajaUsuario(responsable){
    if ($("#cboUsuario").val()==responsable){
        $().alerta('rojo','<justify> No puede desactivar su mismo usuario</justify>');
        return;
    }
    retrieveURL('/CgutUsuarios.msut?ask=bajaUsuario','CgutUsuariosForm');
    $().alerta('verde','El Usuario ha cambiado de estado.');
}

function validarFormularioCategoria(){
    alert(document.getElementById("nombreCategoria").value);
    if(document.getElementById("nombreCategoria").value==""){
        $().alerta('rojo','No se han podido guardar los datos de la Categoria.');
        $().alerta('amarillo','Por favor escriba una<br/>Categor&iacute;a');
    }else{
        retrieveURL('/CgutCategoriasAction.msut?ask=guardarCategoria','CgutCategoriasForm');
        $().alerta('verde','Los datos se han guardado correctamente.');
    }
}
//modificado
function bajaCategoria(){
    retrieveURL('/CgutCategoriasAction.msut?ask=bajaCategoria','CgutCategoriasForm');
    $().alerta('verde','La Categoria ha cambiado de estado.');
}
function validarFormularioIndicadores(){
    if(document.getElementById("cboCategorias2").value<1){
        $().alerta('rojo','No se han podido guardar los datos del Indicador.');
        $().alerta('amarillo','Por favor seleccione una<br/>Categor&iacute;a');
    }else if(document.getElementById("nombreD").value==""){
        $().alerta('rojo','No se han podido guardar los datos del Indicador.');
        $().alerta('amarillo','Por favor escriba el nombre del Indicador');
    }else if(document.getElementById("arJsp").value==""){
        $().alerta('rojo','No se han podido guardar los datos del Indicador.');
        $().alerta('amarillo','Por favor escriba el nombre<br/>del archivo JSP');
    }else if(document.getElementById("arjs").value==""){
        $().alerta('rojo','No se han podido guardar los datos del Indicador.');
        $().alerta('amarillo','Por favor escriba el nombre<br/>del archivo Java Script');
    }else{
        retrieveURL('/CgutInterfaceAction.msut?ask=guardarIndicador','CgutInterfaceForm');
        $().alerta('verde','Los datos se han guardado correctamente.');
    }
}
function bajaIndicador(){
    retrieveURL('/CgutInterfaceAction.msut?ask=bajaIndicador','CgutInterfaceForm');
    $().alerta('verde','El Indicador ha cambiado de estado.');
}

function validarFormularioUni(){
    if(document.getElementById("cvecgut").value==""){
        $().alerta('rojo','No se han podido guardar los datos de la universidad.');
        $().alerta('amarillo','Por favor escriba la clave<br/>de la universidad');
    }else if(document.getElementById("rfc").value==""){
        $().alerta('rojo','No se han podido guardar los datos de la universidad.');
        $().alerta('amarillo','Por favor escriba el RFC de<br/>la universidad');
    }else if(document.getElementById("nombreUni").value==""){
        $().alerta('rojo','No se han podido guardar los datos de la universidad.');
        $().alerta('amarillo','Por favor escriba el nombre de<br/>la universidad');
    }else if(document.getElementById("abreviatura").value==""){
        $().alerta('rojo','No se han podido guardar los datos de la universidad.');
        $().alerta('amarillo','Por favor escriba la abreviatura<br/>de la universidad');
    } else if(document.getElementById("fechaAcreditacion").value==""){
        $().alerta('rojo','No se han podido guardar los datos de la universidad.');
        $().alerta('amarillo','Por favor escriba la fecha<br/>de acreditaci&oacute;n<br/>de la universidad');
    }else{
        var fecha = document.getElementById("fechaAcreditacion").value;
        retrieveURL("/CgutUniversidad.msut?ask=guardarUni&fechaAcred="+fecha,'CgutUniversidadForm');
        document.getElementById("fechaAcreditacion").value="";
        $().alerta('verde','Los datos se han guardado correctamente.');
    }
}
//cambiado
function validarFormularioAreas(){
    if(document.getElementById("nombreArea").value==""){
        $().alerta('rojo','No se han podido guardar los datos del &aacute;rea.');
        $().alerta('amarillo','Por favor escriba el nombre<br/> del &aacute;rea');
    }else{
        retrieveURL('/CgutAreasAction.msut?ask=guardarArea','CgutAreasForm');
        $().alerta('verde','Los datos se han guardado correctamente.');
    }
}
function bajaArea(){
    retrieveURL('/CgutAreasAction.msut?ask=bajaArea','CgutAreasForm');
    $().alerta('verde','El &aacute;rea ha cambiado de estado.');
}

function validarFormularioFamilias(){
    if(document.getElementById("cboAreaFam").value<1){
        $().alerta('rojo','No se han podido guardar los datos de la familia.');
        $().alerta('amarillo','Por favor elija un &aacute;rea<br/>asignada');
    }else if(document.getElementById("nombreFamilia").value==""){
        $().alerta('rojo','No se han podido guardar los datos de la familia.');
        $().alerta('amarillo','Por favor escriba el nombre de<br/>la familia');
    }else if(document.getElementById("nomenclaturaFam").value==""){
        $().alerta('rojo','No se han podido guardar los datos de la familia.');
        $().alerta('amarillo','Por favor escriba el nombre de<br/>la nomenclatura.');
    }else{
        retrieveURL('/CgutFamiliasAction.msut?ask=guardarFamilia','CgutFamiliasForm');
        $().alerta('verde','Los datos se han guardado correctamente.');
    }
}
function bajaFamilia(){
    retrieveURL('/CgutFamiliasAction.msut?ask=bajaFamilia','CgutFamiliasForm');
    $().alerta('verde','La familia ha cambiado de estado.');
}

function validarFormularioProgramas(){
    if(document.getElementById("cboAreaProg").value<1){
        $().alerta('rojo','No se han podido guardar los datos del programa.');
        $().alerta('amarillo','Por favor elija un &aacute;rea<br/>asignada');
    }else if(document.getElementById("cboFamiliaProg").value<1){
        $().alerta('rojo','No se han podido guardar los datos del programa.');
        $().alerta('amarillo','Por favor elija una familia<br/>asignada');
    }else if(document.getElementById("nombrePrograma").value==""){
        $().alerta('rojo','No se han podido guardar los datos del programa.');
        $().alerta('amarillo','Por favor escriba el nombre<br/>del programa');
    }else if(document.getElementById("nomenclaturaProg").value==""){      
        $().alerta('rojo','No se han podido guardar los datos del programa.');
        $().alerta('amarillo','Por favor escriba el nombre<br/>de la nomenclatura');
    } else if(document.getElementById("cboNivel").value<1){
        $().alerta('rojo','No se han podido guardar los datos del programa.');
        $().alerta('amarillo','Por favor elija una nivel');
    }else if(document.getElementById("versionPrograma").value==""){
        $().alerta('rojo','No se han podido guardar los datos del programa.');
        $().alerta('amarillo','Por favor escriba la versi&oacute;n<br/>del programa');
    }else{
        retrieveURL('/CgutProgramasAction.msut?ask=guardarPrograma','CgutProgramasForm');
        $().alerta('verde','Los datos se han guardado correctamente.');
    }
}
function bajaPrograma(){
    retrieveURL('/CgutProgramasAction.msut?ask=bajaPrograma','CgutProgramasForm');
    $().alerta('verde','El programa educativo ha cambiado de estado.');
}

function bajaUniversidad(){
    retrieveURL('/CgutUniversidad.msut?ask=bajaUni','CgutUniversidadForm');
    $().alerta('verde','La universidad ha cambiado de estado.');
}







//cambiado
function mat_Total(save,id_uni,uni,niv){    
    var total=0,total1=0,totalNivel=0,totalNivelTotal=0;
    var u=  $("input[name='finMatTotal']").val();
    if(uni==0){
        var cadena = "";
        for(var i=1;i<u;i++){     
            cadena+=$("input[name='id_u"+i+"']").val();
            var n=$("input[name='t_n"+i+"']").val();
            for(var j=1;j<=n;j++){     
                cadena+=","+$("input[name='niv_"+i+","+j+"']").val();                
                total+=parseInt($("input[name='niv_"+i+","+j+"']").val()); 
                if(!$("form[name='CgutMatriculaTotalForm'] input").hasClass("inputerror")){
                    $("input[name='tot_"+i+"']").val(total);
                }
            }        
            cadena += "-";
            total=0;           
        }  
        //CALCULARA TOTAL POR NIVEL
            var totalxnive=0;
            for(var j=1;j<=n;j++){
                for(var i=1;i<u;i++){         
                    totalxnive+=parseInt($("input[name='niv_"+i+","+j+"']").val());
                }
                $("input[name='totalNiv"+j+"']").val(totalxnive);
                totalxnive = 0;
            }
    }
    
    if(uni==1){
        
        for(var l=1;l<=parseInt($("input[name='tot_n']").val());l++){            
            total1+=parseInt($("input[id='niv_"+id_uni+","+l+"']").val());   
        } 
        for(var t=1;t<u;t++){
            totalNivel+=parseInt($("input[id='niv_"+t+","+niv+"']").val());   
            totalNivelTotal+=parseInt($("input[id='tot_"+t+"']").val());   
        }
        if(!$("form[name='CgutMatriculaTotalForm'] input").hasClass("inputerror")){
            $("input[id='tot_"+id_uni+"']").val(total1);
            $("input[id='totalNiv"+niv+"']").val(totalNivel);
            $("input[name='totalMatTotal']").val(totalNivelTotal);
        }
        
        
        total1=0;             
        totalNivel=0;             
    }
    if(save==1 && uni ==0){
        if($("form[name='CgutMatriculaTotalForm'] input").hasClass("inputerror")){
            $().alerta('rojo','Algunos datos en el formulario de Matr&iacute;cula Total no son v&aacute;lidos.');
        }else{
            //            $( "#dialog-procesando" ).dialog({
            //                height: 180,
            //                modal: true,
            //                show: "fade",
            //                hide: "fade"
            //            }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
            //retrieveURL("/CgutMatriculaTotalAction.msut?mat_total="+cadena,'CgutMatriculaTotalForm');
            var x ={
                "cadena" : cadena
            }
            $.ajax({
                data: x,
                url: "/CgutMatriculaTotalGuardado",
                type: "post",
                beforeSend: function(xhr){
                    $( "#dialog-procesando" ).dialog({
                        height: 180,
                        modal: true,
                        show: "fade",
                        hide: "fade"
                    }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
                },
                success: function(resultado, status, xhr){
                    $( "#dialog-procesando" ).dialog("close");
                    $().alerta('verde','Los datos se han guardado correctamente.');
                },
                failiure: function(response){
                    $().alerta('rojo','Algunos datos en el formulario de Matr&iacute;cula Total no son v&aacute;lidos.');
                }
            });
        //$().alerta('verde','Los datos se han guardado correctamente.');
        }
    }
    return cadena;    
}

function validarFormularioServicios(){
    if ( $("#nombre").val() == "" ){
        $().alerta('rojo','No se han podido guardar los datos del servicio.');
        $().alerta('amarillo','Por favor ingresa un nombre');
    }else if ( $("#descripcion").val() == "" ){
        $().alerta('rojo','No se han podido guardar los datos del servicio.');
        $().alerta('amarillo','Por favor ingresa la descripci&oacute;n del servicio');
    }else{
        retrieveURL("/CgutServicios.msut?ask=guardar",'CgutServiciosForm');
        $().alerta('verde','Los datos se han guardado correctamente.');
    }
}

//agregado
function validarFormularioOrg(){
    if($("form[name='CgutOrganismosForm'] textarea[id='nombreOrganismo']").val()==""){
        $().alerta('rojo','No se han podido guardar los datos del organismo.');
        $().alerta('amarillo','Por favor escriba el nombre<br/> del organismo');
    }
    else if($("form[name='CgutOrganismosForm'] input[id='sigla']").val()==""){
        $().alerta('rojo','No se han podido guardar los datos del organismo.');
        $().alerta('amarillo','Por favor escriba la sigla<br/> del organismo');
    }
    if($("form[name='CgutOrganismosForm'] input[name='extranjero']").val()==""){
        $().alerta('rojo','No se han podido guardar los datos del organismo.');
        $().alerta('amarillo','Por favor escriba el nombre del Pa&iacute;s<br/> del organismo');
    }
     
    else{
        retrieveURL('/CgutOrganismos.msut?ask=guardarOrganismo','CgutOrganismosForm');
        $().alerta('verde','Los datos se han guardado correctamente.');
    }
}

function organismoNacional(){
    setTimeout(function(){             
        if($("form[name='CgutOrganismosForm'] input[name='extranjero']").val()==0){
            $("form[name='CgutOrganismosForm'] input[id='radio1']").removeAttr("checked")
            $("form[name='CgutOrganismosForm'] input[id='radio2']").attr("checked","checked");
            $("form[name='CgutOrganismosForm'] input[id='radio2']").button("refresh");
            $("form[name='CgutOrganismosForm'] input[id='radio1']").button("refresh");
        }
        if($("form[name='CgutOrganismosForm'] input[name='extranjero']").val()==1){
            //alert($("form[name='CgutOrganismosForm'] input[id='radio1']").is(":checked"));
            $("form[name='CgutOrganismosForm'] input[id='radio2']").removeAttr("checked");
            $("form[name='CgutOrganismosForm'] input[id='radio1']").attr("checked","checked");
            $("form[name='CgutOrganismosForm'] input[id='radio2']").button("refresh");
            $("form[name='CgutOrganismosForm'] input[id='radio1']").button("refresh");          
        }
    },100);
}

function cambiarPais(){    
    if($("form[name='CgutOrganismosForm'] input[name='extranjero']").val()==0 || $("form[name='CgutOrganismosForm'] input[name='extranjero']").val()==""){
        alert($("form[name='CgutOrganismosForm'] input[name='extranjero']").val());
        $("form[name='CgutOrganismosForm'] input[id='pais']").val("M&eacute;xico");
    }
    
}