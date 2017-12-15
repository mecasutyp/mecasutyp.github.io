var sug1,sug2,sug3, modSelected, selected, aniosInco, nuevoingreso, matriculatotal, clasificacion;
var iterarModelos = new Array();
var iterarServicios = new Array();
var iterarEdificios = new Array();

//Borrar nivel educativo de Modelos
function borrarNEM(nivel){
    //Recuperamos los niveles educativos que ha seleccionado el usuario
    var contadorN = 0;
    for (var i = 0; i < document.getElementById("lstSeleccionados").options.length; i++)
    {
        if(document.getElementById("lstSeleccionados").options[ i ].text.substring(0,document.getElementById("lstSeleccionados").options[ i ].text.indexOf(" ")) == nivel)
            contadorN++;
    }
    if(contadorN == 1)
        $("[name='modelosNivel"+nivel+"']").remove();
}
//Agregar nivel educativo de Modelos
function agregarNEM(nivel){
    var models = $('[name="modelos"]').val();
    var modelos = new Array();
    //Recuperar los modelos educativos activados por la CGUT previamente cargados en una propiedad del formulario
    while(models.indexOf(",") != -1){
        modelos.push(models.substring(0, models.indexOf(",")));
        models = models.substring(models.indexOf(",") + 1, models.length);
        
    }
    modelos.push(models.substring(0, models.length));

    //Creamos los Check
    if($("[name='modelosNivel"+nivel+"']").length == 0){
        var trChecks = "";
        var noCheck = 0;
        trChecks+=' <tr name="modelosNivel'+nivel+'"><td style="width:auto;" align="right"><label>Modalidades que se ofrecen para el nivel '+nivel+':</label></td><td style="width:auto;" align="left">\n\
                    <div id="conjuntoChecks'+nivel+'" align="left">';
        if(nivel == 'TSU'){
            modelos.forEach(function(domEle){
                trChecks+='<input type="checkbox"';
               // console.log("iterar "+iterarModelos.indexOf(nivel+domEle)+"   ("+nivel+domEle+")");
                if(iterarModelos.indexOf(nivel+domEle) != -1){
                    trChecks+=' checked="checked" ';
                }
                trChecks+='id="check'+nivel+domEle+'" /><label for="check'+nivel+domEle+'">'+domEle+'</label>';
                noCheck++;
            });
        }else{
            trChecks+='<input type="checkbox"';
            trChecks+=' checked="checked" ';
            trChecks+='id="check'+nivel+'REGULAR" /><label for="check'+nivel+'REGULAR">REGULAR</label>';
        }

        trChecks+='</div></td></tr>';
        $(trChecks).appendTo("#tablaModelos");
        $("#conjuntoChecks"+nivel).buttonset();
    }
}

function validarFormulario(iniciar){
    var guardar = true;
    var datosGen = 0;
    var datosDom = 0;
    var datosAcad = 0;
    var datosCon = 0;

    //Validar Informacion general
    
    /*Abreviatura */
    if ( $("#abreviatura").val()==""){
        $('#tabUni').globo('update', '<center>Por favor<br/>ingrese<br/>la abreviatura<center>');
        $('#tabUni').globo('show');
        $('#tabs').tabs("select", 0);
        datosGen++;
        guardar = false;
    }
    
    /*RFC */
    if ( $("#rfc").val()==""){
        $('#tabUni').globo('update', '<center>Por favor<br/>ingrese<br/>el RFC<center>');
        $('#tabUni').globo('show');
        $('#tabs').tabs("select", 0);
        datosGen++;
        guardar = false;
    }
    
    /*Fecha de Acreditación*/
    if ( $("#fechaCreacion").val()==""){
        $('#tabUni').globo('update', '<center>Por favor<br/>seleccione<br/>una fecha<br/>v&aacute;lida<center>');
        $('#tabUni').globo('show');
        $('#tabs').tabs("select", 0);
        datosGen++;
        guardar = false;
    }
    /*Fecha de Acreditación*/
    
    /*SERVICIOS*/
    $("input[name='servSeleccionados']").val("");
    $("select[name='lstServiciosSeleccionados'] option:checked").each(function(index, domEle){
        if($("input[name='servSeleccionados']").val() == "")
            $("input[name='servSeleccionados']").val($(domEle).val());
        else
            $("input[name='servSeleccionados']").val($("input[name='servSeleccionados']").val() + "," + $(domEle).val());
    });
    $("input[name='edifSeleccionados']").val("");
    $("select[name='lstEdificiosSeleccionados'] option:checked").each(function(index, domEle){
        if($("input[name='edifSeleccionados']").val() == "")
            $("input[name='edifSeleccionados']").val($(domEle).val());
        else
            $("input[name='edifSeleccionados']").val($("input[name='edifSeleccionados']").val() + "," + $(domEle).val());
    });
    if($("input[name='servSeleccionados']").val() == ""){
        $('#tabUni').globo('update', '<center>Por favor<br/>elija un<br/>al menos un<br/>Servicio<center>');
        $('#tabUni').globo('show');
        $('#tabs').tabs("select", 0);
        datosGen++;
        guardar = false;
    }
    /*EDIFICIOS*/
    if($("input[name='edifSeleccionados']").val() == ""){
        $('#tabUni').globo('update', '<center>No se han<br/>seleccionado<br/>edificios<br/><a onclick="modal(\'Informaci&oacute;n\',\'Si su universidad cuenta con edificios, es necesario que introdusca la informaci&oacute;n, s&oacute;lo es opcional para universidades que a&uacute;n no cuentan con instalaci&oacute;nes oficiales\');"><span class="ui-icon ui-icon-info" style="float:left;"></span>Click aqu&iacute;</a><center>');
        $('#tabUni').globo('show');
        $('#tabs').tabs("select", 0);
        datosGen++;
    }
    /*
     *CERTIFICACIONES
     *
    if(!($("#orgInt").next().hasClass("ui-state-active"))){
        $('#tabUni').globo('update', '<center>No se han<br/>verificado los<br/>Organismos<br/>Internacionales<center>');
        $('#tabUni').globo('show');
        $('#tabs').tabs("select", 0);
        datosGen++;
        guardar = false;
    }
    if(!($("#certUni").next().hasClass("ui-state-active"))){
        $('#tabUni').globo('update', '<center>No se han<br/>verificado las<br/>Certificaciones<center>');
        $('#tabUni').globo('show');
        $('#tabs').tabs("select", 0);
        datosGen++;
        guardar = false;
    }
    */
    
    if (datosGen > 1){
        $('#tabUni').globo('update', '<center>Por favor<br/>verifique los datos<br/>generales<center>');
        $('#tabUni').globo('show');
        $('#tabs').tabs("select", 0);
    }else if(datosGen == 0){
        $('#tabUni').globo('reset');
    }
    //Validar Domicilio
    //Estado
    if ($('#cboEstado').val() == -1){
        $('#tabDom').globo('update', '<center>Por favor<br/>elija un<br/>estado<center>');
        $('#tabDom').globo('show');
        $("#cboEstado").globo({
            alignTo: 'target',
            offsetX: 5,
            alignX: 'center'
        }).globo("enable").globo('update', '<center>Por favor elija un estado<center>').css("borderColor", "red");
        $('#tabs').tabs("select", 1);
        datosDom++;
        guardar = false;
    }else{
        $("#cboEstado").globo("disable").css("borderColor", "#d1c7ac");
        $('#cboEstado').globo('reset');
    }
    //Municipio
    if ($('input[name="municipio"]').val() == ""){
        $('#tabDom').globo('update', '<center>Por favor<br/>escriba un municipio<br/>v&aacute;lido<center>');
        $('#tabDom').globo('show');
        $("input[name='municipio']").globo({
            alignTo: 'target',
            offsetX: 5,
            alignX: 'center'
        }).globo("enable").globo('update', 'Por favor escriba un municipio v&aacute;lido').css("borderColor", "red");
        $('#tabs').tabs("select", 1);
        datosDom++;
        guardar = false;
    }else{
        $('input[name="municipio"]').globo("disable").css("borderColor", "#d1c7ac");
        $('input[name="municipio"]').globo('reset');
    }
    //Colonia
    if ($('input[name="colonia"]').val() == ""){
        $('#tabDom').globo('update', '<center>Por favor<br/>escriba una colonia<br/>v&aacute;lida<center>');
        $('#tabDom').globo('show');
        $("input[name='colonia']").globo({
            alignTo: 'target',
            offsetX: 5,
            alignX: 'center'
        }).globo("enable").globo('update', 'Por favor escriba una colonia v&aacute;lida').css("borderColor", "red");
        $('#tabs').tabs("select", 1);
        datosDom++;
        guardar = false;
    }else{
        $('input[name="colonia"]').globo("disable").css("borderColor", "#d1c7ac");
        $('input[name="colonia"]').globo('reset');
    }
    //Calle
    if ($('input[name="calle"]').val() == ""){
        $('#tabDom').globo('update', '<center>Por favor<br/>escriba una calle<br/>v&aacute;lida<center>');
        $('#tabDom').globo('show');
        $("input[name='calle']").globo({
            alignTo: 'target',
            offsetX: 5,
            alignX: 'center'
        }).globo("enable").globo('update', 'Por favor escriba una calle v&aacute;lida').css("borderColor", "red");
        $('#tabs').tabs("select", 1);
        datosDom++;
        guardar = false;
    }else{
        $('input[name="calle"]').globo("disable").css("borderColor", "#d1c7ac");
        $('input[name="calle"]').globo('reset');
    }
    //Número
    if ($('input[name="numero"]').val() == ""){
        $('#tabDom').globo('update', '<center>Por favor<br/>escriba un numero<br/>v&aacute;lido<center>');
        $('#tabDom').globo('show');
        $("input[name='numero']").globo({
            alignTo: 'target',
            offsetX: 5,
            alignX: 'center'
        }).globo("enable").globo('update', 'Por favor escriba una numero v&aacute;lido').css("borderColor", "red");
        $('#tabs').tabs("select", 1);
        datosDom++;
        guardar = false;
    }else{
        $('input[name="numero"]').globo("disable").css("borderColor", "#d1c7ac");
        $('input[name="numero"]').globo('reset');
    }
    //Codigo Postal
    if (!/^([0-9]{5})*$/.test($('input[name="cp"]').val()) || $('input[name="cp"]').val() == ""){
        $('#tabDom').globo('update', '<center>Por favor<br/>escriba un c&oacute;digo postal<br/>v&aacute;lido<center>');
        $('#tabDom').globo('show');
        $("input[name='cp']").globo({
            alignTo: 'target',
            offsetX: 5,
            alignX: 'center'
        }).globo("enable").globo('update', '<center>Por favor escriba un c&oacute;digo postal v&aacute;lido <br/> Ejemplo: 55770</center>').css("borderColor", "red");
        $('#tabs').tabs("select", 1);
        datosDom++;
        guardar = false;
    }else{
        $('input[name="cp"]').globo("disable").css("borderColor", "#d1c7ac");
        $('input[name="cp"]').globo('reset');
    }

    if (datosDom > 1){
        $('#tabDom').globo('update', '<center>Por favor<br/>verifique los datos<br/>del domicilio<center>');
        $('#tabDom').globo('show');
        $('#tabs').tabs("select", 1);
    }else if(datosDom == 0){
        $('#tabDom').globo('reset');
    }
    
    
    
    /*
     *VALIDACIONES PARA LOS RESPONSABLES
     */
    
    /*RECTOR*/
    
    //NOMBRE
    if ( $("input[name='txtRecNombre']").val()==""){
        $("#tabCon").globo("update", "<center>Por favor<br/>elija un nombre para<br/>el Rector<center>");
        $("#tabCon").globo("show");
        $("input[name='txtRecNombre']").globo({
            alignTo: 'target',
            offsetX: 5,
            alignX: 'center'
        }).globo("enable").globo('update', '<center>Por favor escriba un nombre para el Rector</center>').css("borderColor", "red");
        $('#tabs').tabs("select", 2);
        guardar = false;
        datosCon++;
    }else{
        $('input[name="txtRecNombre"]').globo("disable").css("borderColor", "#d1c7ac");
        $('input[name="txtRecNombre"]').globo('reset');
    }
    
    //APELLIDO PATERNO
    if ( $("input[name='txtRecApaterno']").val()==""){
        $("#tabCon").globo("update", "<center>Por favor<br/>elija un Apellido Paterno para<br/>el Rector<center>");
        $("#tabCon").globo("show");
        $("input[name='txtRecApaterno']").globo({
            alignTo: 'target',
            offsetX: 5,
            alignX: 'center'
        }).globo("enable").globo('update', '<center>Por favor escriba un Apellido Paterno para el Rector</center>').css("borderColor", "red");
        $('#tabs').tabs("select", 2);
        guardar = false;
        datosCon++;
    }else{
        $('input[name="txtRecApaterno"]').globo("disable").css("borderColor", "#d1c7ac");
        $('input[name="txtRecApaterno"]').globo('reset');
    }
    
    //APPELIDO MATERNO
    if ( $("input[name='txtRecAmaterno']").val()==""){
        $("#tabCon").globo("update", "<center>Por favor<br/>elija un Apellido Materno para<br/>el Rector<center>");
        $("#tabCon").globo("show");
        $("input[name='txtRecAmaterno']").globo({
            alignTo: 'target',
            offsetX: 5,
            alignX: 'center'
        }).globo("enable").globo('update', '<center>Por favor escriba un Apellido Materno para el Rector</center>').css("borderColor", "red");
        $('#tabs').tabs("select", 2);
        guardar = false;
        datosCon++;
    }else{
        $('input[name="txtRecAmaterno"]').globo("disable").css("borderColor", "#d1c7ac");
        $('input[name="txtRecAmaterno"]').globo('reset');
    }
    //LADA
    if ( $("input[name='txtRecLada']").val()=="" || $("input[name='txtRecLada']").val().length < 2 || isNaN($("input[name='txtRecLada']").val())){
        $("#tabCon").globo("update", "<center>Por favor<br/>agregue una Lada<br/>para el Rector<center>");
        $("#tabCon").globo("show");
        $("input[name='txtRecLada']").globo({
            alignTo: 'target',
            offsetX: 5,
            alignX: 'center'
        }).globo("enable").globo("update", "Por favor escriba una Lada v&aacute;lida, la longitud m&iacute;nima es de 2 caract&eacute;res. ej. '52'").css("borderColor", "red");
        $('#tabs').tabs("select", 2);
        guardar = false;
        datosCon++;
    }else{
        $('input[name="txtRecLada"]').globo("disable").css("borderColor", "#d1c7ac");
        $('input[name="txtRecLada"]').globo('reset');
    }
    
    //TELEFONO
    if ( $("input[name='txtRecTelefono']").val()=="" || $("input[name='txtRecTelefono']").val().length < 8){
        $("#tabCon").globo("update", "<center>Por favor<br/>agregue un tel&eacute;fono<br/>para el Rector<center>");
        $("#tabCon").globo("show");
        $("input[name='txtRecTelefono']").globo({
            alignTo: 'target',
            offsetX: 5,
            alignX: 'center'
        }).globo("enable").globo("update", "Por favor escriba un tel&eacute;fono v&aacute;lido, la longitud m&iacute;nima es de 8 caract&eacute;res. ej. '59324576 Ext. 554'").css("borderColor", "red");
        $('#tabs').tabs("select", 2);
        guardar = false;
        datosCon++;
    }else{
        $('input[name="txtRecTelefono"]').globo("disable").css("borderColor", "#d1c7ac");
        $('input[name="txtRecTelefono"]').globo('reset');
    }
    
    //MAIL
    if ( /[\w-\.]{3,}@([\w-]{2,}\.)*([\w-]{2,}\.)[\w-]{2,4}/.test($("input[name='txtRecCorreo']").val()) == false){
        $("#tabCon").globo("update", "<center>Por favor<br/>agregue un E-Mail<br/>para el Rector<center>");
        $("#tabCon").globo("show");
        $("input[name='txtRecCorreo']").globo({
            alignTo: 'target',
            offsetX: 5,
            alignX: 'center'
        }).globo("enable").globo("update", "Por favor escriba un E-Mail v&aacute;lido").css("borderColor", "red");
        $("#tabs").tabs("select", 2);
        datosCon++;
        guardar = false;
    }else{
        $('input[name="txtRecCorreo"]').globo("disable").css("borderColor", "#d1c7ac");
        $('input[name="txtRecCorreo"]').globo('reset');
    }
    
        
    /*
     *RESPONSABLE
     */
    
    //NOMBRE
    if ( $("input[name='txtResNombre']").val()==""){
        $("#tabCon").globo("update", "<center>Por favor<br/>elija un nombre para<br/>el Responsable<center>");
        $("input[name='txtResNombre']").globo({
            alignTo: 'target',
            offsetX: 5,
            alignX: 'center'
        }).globo("enable").globo('update', '<center>Por favor escriba un nombre para el Responsable</center>').css("borderColor", "red");
        $('#tabs').tabs("select", 2);
        guardar = false;
        datosCon++;
    }else{
        $('input[name="txtResNombre"]').globo("disable").css("borderColor", "#d1c7ac");
        $('input[name="txtResNombre"]').globo('reset');
    }
    
    //APELLIDO PATERNO
    if ( $("input[name='txtResApaterno']").val()==""){
        $("#tabCon").globo("update", "<center>Por favor<br/>elija un Apellido Paterno para<br/>el Responsable<center>");
        $("input[name='txtResApaterno']").globo({
            alignTo: 'target',
            offsetX: 5,
            alignX: 'center'
        }).globo("enable").globo('update', '<center>Por favor escriba un Apellido Paterno para el Responsable</center>').css("borderColor", "red");
        $('#tabs').tabs("select", 2);
        guardar = false;
        datosCon++;
    }else{
        $('input[name="txtResApaterno"]').globo("disable").css("borderColor", "#d1c7ac");
        $('input[name="txtResApaterno"]').globo('reset');
    }
    
    //APPELIDO MATERNO
    if ( $("input[name='txtResAmaterno']").val()==""){
        $("#tabCon").globo("update", "<center>Por favor<br/>elija un Apellido Materno para<br/>el Responsable<center>");
        $("input[name='txtResAmaterno']").globo({
            alignTo: 'target',
            offsetX: 5,
            alignX: 'center'
        }).globo("enable").globo('update', '<center>Por favor escriba un Apellido Materno para el Responsable</center>').css("borderColor", "red");
        $('#tabs').tabs("select", 2);
        guardar = false;
        datosCon++;
    }else{
        $('input[name="txtResAmaterno"]').globo("disable").css("borderColor", "#d1c7ac");
        $('input[name="txtResAmaterno"]').globo('reset');
    }
    
    //CARGO
    if ( $("input[name='txtResCargo']").val()==""){
        $("#tabCon").globo("update", "<center>Por favor<br/>ingrese el cargo del<br/>el Responsable<center>");
        $("input[name='txtResCargo']").globo({
            alignTo: 'target',
            offsetX: 5,
            alignX: 'center'
        }).globo("enable").globo('update', '<center>Por favor escriba el cargo del Responsable</center>').css("borderColor", "red");
        $('#tabs').tabs("select", 2);
        guardar = false;
        datosCon++;
    }else{
        $('input[name="txtResCargo"]').globo("disable").css("borderColor", "#d1c7ac");
        $('input[name="txtResCargo"]').globo('reset');
    }
     //LADA
    if ( $("input[name='txtResLada']").val()=="" || $("input[name='txtResLada']").val().length < 2 || isNaN($("input[name='txtResLada']").val())){
        $("#tabCon").globo("update", "<center>Por favor<br/>agregue una Lada<br/>para el Responsable<center>");
        $("#tabCon").globo("show");
        $("input[name='txtResLada']").globo({
            alignTo: 'target',
            offsetX: 5,
            alignX: 'center'
        }).globo("enable").globo("update", "Por favor escriba una Lada v&aacute;lida, la longitud m&iacute;nima es de 2 caract&eacute;res. ej. '52'").css("borderColor", "red");
        $('#tabs').tabs("select", 2);
        guardar = false;
        datosCon++;
    }else{
        $('input[name="txtResLada"]').globo("disable").css("borderColor", "#d1c7ac");
        $('input[name="txtResLada"]').globo('reset');
    }
    //TELEFONO
    if ( $("input[name='txtResTelefono']").val()=="" || $("input[name='txtResTelefono']").val().length < 8){
        $("#tabCon").globo("update", "<center>Por favor<br/>agregue un tel&eacute;fono<br/>para el Responsable<center>");
        $("input[name='txtResTelefono']").globo({
            alignTo: 'target',
            offsetX: 5,
            alignX: 'center'
        }).globo("enable").globo("update", "Por favor escriba un tel&eacute;fono v&aacute;lido, la longitud m&iacute;nima es de 8 caract&eacute;res. ej. '59324576 Ext. 554'").css("borderColor", "red");
        $('#tabs').tabs("select", 2);
        guardar = false;
        datosCon++;
    }else{
        $('input[name="txtResTelefono"]').globo("disable").css("borderColor", "#d1c7ac");
        $('input[name="txtResTelefono"]').globo('reset');
    }
    
    //MAIL
    if ( /[\w-\.]{3,}@([\w-]{2,}\.)*([\w-]{2,}\.)[\w-]{2,4}/.test($("input[name='txtResCorreo']").val()) == false){
        $("#tabCon").globo("update", "<center>Por favor<br/>agregue un E-Mail<br/>para el Responsable<center>");
        $("#tabCon").globo("show");
        $("input[name='txtResCorreo']").globo({
            alignTo: 'target',
            offsetX: 5,
            alignX: 'center'
        }).globo("enable").globo("update", "Por favor escriba un E-Mail v&aacute;lido").css("borderColor", "red");
        $("#tabs").tabs("select", 2);
        datosCon++;
        guardar = false;
    }else{
        $('input[name="txtResCorreo"]').globo("disable").css("borderColor", "#d1c7ac");
        $('input[name="txtResCorreo"]').globo('reset');
    }
    
    /*CAPTURISTA*/
    
    //NOMBRE
    if ( $("input[name='txtCapNombre']").val()==""){
        $("#tabCon").globo("update", "<center>Por favor<br/>elija un nombre para<br/>el Capturista<center>");
        $("#tabCon").globo("show");
        $("input[name='txtCapNombre']").globo({
            alignTo: 'target',
            offsetX: 5,
            alignX: 'center'
        }).globo("enable").globo('update', '<center>Por favor escriba un nombre para el Capturista</center>').css("borderColor", "red");
        $('#tabs').tabs("select", 2);
        guardar = false;
        datosCon++;
    }else{
        $('input[name="txtCapNombre"]').globo("disable").css("borderColor", "#d1c7ac");
        $('input[name="txtCapNombre"]').globo('reset');
    }
    
    //APELLIDO PATERNO
    if ( $("input[name='txtCapApaterno']").val()==""){
        $("#tabCon").globo("update", "<center>Por favor<br/>elija un Apellido Paterno para<br/>el Capturista<center>");
        $("#tabCon").globo("show");
        $("input[name='txtCapApaterno']").globo({
            alignTo: 'target',
            offsetX: 5,
            alignX: 'center'
        }).globo("enable").globo('update', '<center>Por favor escriba un Apellido Paterno para el Capturista</center>').css("borderColor", "red");
        $('#tabs').tabs("select", 2);
        guardar = false;
        datosCon++;
    }else{
        $('input[name="txtCapApaterno"]').globo("disable").css("borderColor", "#d1c7ac");
        $('input[name="txtCapApaterno"]').globo('reset');
    }
    
    //APPELIDO MATERNO
    if ( $("input[name='txtCapAmaterno']").val()==""){
        $("#tabCon").globo("update", "<center>Por favor<br/>elija un Apellido Materno para<br/>el Capturista<center>");
        $("#tabCon").globo("show");
        $("input[name='txtCapAmaterno']").globo({
            alignTo: 'target',
            offsetX: 5,
            alignX: 'center'
        }).globo("enable").globo('update', '<center>Por favor escriba un Apellido Materno para el Capturista</center>').css("borderColor", "red");
        $('#tabs').tabs("select", 2);
        guardar = false;
        datosCon++;
    }else{
        $('input[name="txtCapAmaterno"]').globo("disable").css("borderColor", "#d1c7ac");
        $('input[name="txtCapAmaterno"]').globo('reset');
    }
    
    //CARGO
    if ( $("input[name='txtCapCargo']").val()==""){
        $("#tabCon").globo("update", "<center>Por favor<br/>ingrese el cargo del<br/>el Capturista<center>");
        $("#tabCon").globo("show");
        $("input[name='txtCapCargo']").globo({
            alignTo: 'target',
            offsetX: 5,
            alignX: 'center'
        }).globo("enable").globo('update', '<center>Por favor escriba el cargo del Capturista</center>').css("borderColor", "red");
        $('#tabs').tabs("select", 2);
        guardar = false;
        datosCon++;
    }else{
        $('input[name="txtCapCargo"]').globo("disable").css("borderColor", "#d1c7ac");
        $('input[name="txtCapCargo"]').globo('reset');
    }
     //LADA
    if ( $("input[name='txtCapLada']").val()=="" || $("input[name='txtCapLada']").val().length < 2 || isNaN($("input[name='txtCapLada']").val())){
        $("#tabCon").globo("update", "<center>Por favor<br/>agregue una Lada<br/>para el Capturista<center>");
        $("#tabCon").globo("show");
        $("input[name='txtCapLada']").globo({
            alignTo: 'target',
            offsetX: 5,
            alignX: 'center'
        }).globo("enable").globo("update", "Por favor escriba una Lada v&aacute;lida, la longitud m&iacute;nima es de 2 caract&eacute;res. ej. '52'").css("borderColor", "red");
        $('#tabs').tabs("select", 2);
        guardar = false;
        datosCon++;
    }else{
        $('input[name="txtCapLada"]').globo("disable").css("borderColor", "#d1c7ac");
        $('input[name="txtCapLada"]').globo('reset');
    }
    //TELEFONO
    if ( $("input[name='txtCapTelefono']").val()=="" || $("input[name='txtCapTelefono']").val().length < 8){
        $("#tabCon").globo("update", "<center>Por favor<br/>agregue un tel&eacute;fono<br/>para el Capturista<center>");
        $("#tabCon").globo("show");
        $("input[name='txtCapTelefono']").globo({
            alignTo: 'target',
            offsetX: 5,
            alignX: 'center'
        }).globo("enable").globo("update", "Por favor escriba un tel&eacute;fono v&aacute;lido, la longitud m&iacute;nima es de 8 caract&eacute;res. ej. '59324576 Ext. 554'").css("borderColor", "red");
        $('#tabs').tabs("select", 2);
        guardar = false;
        datosCon++;
    }else{
        $('input[name="txtCapTelefono"]').globo("disable").css("borderColor", "#d1c7ac");
        $('input[name="txtCapTelefono"]').globo('reset');
    }
    
    //MAIL
    if ( /[\w-\.]{3,}@([\w-]{2,}\.)*([\w-]{2,}\.)[\w-]{2,4}/.test($("input[name='txtCapCorreo']").val()) == false){
        $("#tabCon").globo("update", "<center>Por favor<br/>agregue un E-Mail<br/>para el Capturista<center>");
        $("#tabCon").globo("show");
        $("input[name='txtCapCorreo']").globo({
            alignTo: 'target',
            offsetX: 5,
            alignX: 'center'
        }).globo("enable").globo("update", "Por favor escriba un E-Mail v&aacute;lido").css("borderColor", "red");
        $("#tabs").tabs("select", 2);
        datosCon++;
        guardar = false;
    }else{
        $('input[name="txtCapCorreo"]').globo("disable").css("borderColor", "#d1c7ac");
        $('input[name="txtCapCorreo"]').globo('reset');
    }
    
    if (datosCon > 1){
        $("#tabCon").globo("update", "<center>Por favor<br/>verifique los datos<br/>de Contacto<center>");
        $("#tabCon").globo("show");
        $("#tabs").tabs("select", 2);
    }else if(datosCon == 0){
        $("#tabCon").globo("reset");
    }


    /*
     *Validar datos Academicos
     */
    //Datos Generales
    if($("div[name='tabDG'] input[style*='red']").length != 0){
        $('#tabAca').globo('update', '<center>Por favor<br/>revise los<br/>datos generales<center>');
        $('#tabAca').globo('show');
        $('#tabs').tabs("select", 3);
        datosAcad++;
        guardar = false
    }
    //Programas educativos
    if(selected == ""){
        $('#tabAca').globo('update', '<center>Por favor<br/>agregue al menos un<br/>programa educativo<center>');
        $('#tabAca').globo('show');
        $('#tabs').tabs("select", 3);
        datosAcad++;
        guardar = false
    }
    //Modelos educativos
    modSelected = "-";
    $("div[id^='conjuntoChecks']").each(function(index, domEle){
        var nAct = domEle.id.substring(14, domEle.id.length);
        var contModelos = 0;
        modSelected = modSelected.concat(nAct);
        $("#"+domEle.id+" > input[id^='check"+nAct+"']").each(function(index2, domEle2){
            if(domEle2.checked == true){
                modSelected = modSelected.concat(",").concat(domEle2.id.substring(5+nAct.length, domEle2.id.length));
                contModelos++;
            }
        });
        modSelected = modSelected.concat("-");
        if(contModelos == 0){
            $('#tabAca').globo('update', '<center>Por favor<br/>agregue al menos un<br/>modelo por nivel<center>');
            $('#tabAca').globo('show');
            $('#tabs').tabs("select", 3);
            datosAcad++;
            guardar = false
            return false;
        }
    });
    if(selected == ""){
        $('#tabAca').globo('update', '<center>Por favor<br/>agregue al menos un<br/>programa educativo<center>');
        $('#tabAca').globo('show');
        $('#tabs').tabs("select", 3);
        datosAcad++;
        guardar = false
    }

    if (datosAcad > 1){
        $('#tabAca').globo('update', '<center>Por favor<br/>verifique los datos<br/>acad&eacute;micos<center>');
        $('#tabAca').globo('show');
        $('#tabs').tabs("select", 3);
        guardar = false;
    }else if(datosAcad == 0){
        $('#tabAca').globo('reset');
    }
    //Guardar si los datos son correctos
    /*document.location.href = '/registroUniversidad.msut?ask=guardar&seleccionados='+selected+'&lstAniosInc='+aniosInco+'&modSeleccionados='+modSelected+'&respCaptura='+$("#responsableC").val()+'&cboRector='+$("cboRector").val()+'&cboContacto='+$("#cboContacto").val();
    $().alerta('verde','Datos guardados correctamente.');
     */
    if(guardar == true){       
        if (iniciar == false){           
            retrieveURL('/registroUniversidad.msut?ask=guardar&seleccionados='+selected+'&lstAniosInc='+aniosInco+'&lstNuevoIngreso='+nuevoingreso+'&lstMatriculaTotal='+matriculatotal+'&lstClasificacion='+clasificacion
                +'&modSeleccionados='+modSelected,'registroUniversidadForm');
            $().alerta('verde','Datos guardados correctamente.');
        }
        $("*").globo('reset');       
        return true;
    }else{      
        //retrieveURL('/registroUniversidad.msut?ask=guardar&seleccionados='+selected+'&lstAniosInc='+aniosInco+'&modSeleccionados='+modSelected,'registroUniversidadForm');
retrieveURL('/registroUniversidad.msut?ask=guardar&seleccionados='+selected+'&lstAniosInc='+aniosInco+'&lstNuevoIngreso='+nuevoingreso+'&lstMatriculaTotal='+matriculatotal+'&lstClasificacion='+clasificacion
                +'&modSeleccionados='+modSelected,'registroUniversidadForm');
$().alerta('amarillo','Los datos se han guardado parcialmente, pero para iniciar la evaluaci&oacute;n debe llenarse toda la informaci&oacute;n.');
        return false;
    }
}

function corregirMail(numero, sug){
    $('#mailBox'+numero).val(sug);
    $('.email_suggestion'+numero).html('').slideUp();
}

$(document).ready(function() {
    modSelected = $('[name="modSeleccionados"]').val(), selected = $('[name="seleccionados"]').val();
    selected = $('[name="seleccionados"]').val();
    aniosInco = $('[name="lstAniosInc"]').val();
    nuevoingreso = $('[name="lstNuevoIngreso"]').val();
    matriculatotal= $('[name="lstMatriculaTotal"]').val();
    clasificacion= $('[name="lstClasificacion"]').val();

    $("select[name='lstServiciosSeleccionados']").asmSelect({
        addItemTarget: 'bottom',
        animate: true,
        highlight: true
    }).after($("<button>Seleccionar todos</button>").button({
        icons: {
            primary: "ui-icon-plus"
        }
    }).click(function() {
        $("select[name='lstServiciosSeleccionados']").children().attr("selected", "selected").end().change();
        return false;
    }));

    $("select[name='lstEdificiosSeleccionados']").asmSelect({
        addItemTarget: 'bottom',
        animate: true,
        highlight: true
    }).after($("<button>Seleccionar todos</button>").button({
        icons: {
            primary: "ui-icon-plus"
        }
    }).click(function() {
        $("select[name='lstEdificiosSeleccionados']").children().attr("selected", "selected").end().change();
        return false;
    }));

    $('.soloNumeros').on('blur', function() {
        if (this.value == "")
            this.value = 0;
        if (!/^([0-9])*$/.test(this.value)){
            $().alerta("amarillo", "El valor '"+ this.value +"' no es v&aacute;lido");
            $(this).globo("enable").globo('update', 'Por favor escriba una numero v&aacute;lido').css("borderColor", "red");
            return;
        } else{
            $(this).globo("disable").css("borderColor", "#d1c7ac").globo('reset');
            this.value=Number(this.value);
        }
    });
    modSelected = $('input[name="modSeleccionados"]').val().concat(',');
    while(modSelected != ""){
        iterarModelos.push(modSelected.substring(0, modSelected.indexOf(",")));
        modSelected = modSelected.substring(modSelected.indexOf(",") + 2, modSelected.lenght);
       // console.log("iterar pus"+modSelected.substring(0, modSelected.indexOf(",")));
    }
    
    serSelected = $('input[name="servSeleccionados"]').val().concat(',');
    while(serSelected != ""){
        iterarServicios.push(serSelected.substring(0, serSelected.indexOf(",")));
        serSelected = serSelected.substring(serSelected.indexOf(",") + 2, serSelected.lenght);
    }

    $(iterarServicios).each(function(index, domEle){
        $("select[name='lstServiciosSeleccionados'] option[value='"+domEle+"']").attr("selected",true).change();
    });

    ediSelected = $('input[name="edifSeleccionados"]').val().concat(',');
    while(ediSelected != ""){
        iterarEdificios.push(ediSelected.substring(0, ediSelected.indexOf(",")));
        ediSelected = ediSelected.substring(ediSelected.indexOf(",") + 2, ediSelected.lenght);
    }
    $(iterarEdificios).each(function(index, domEle){
        $("select[name='lstEdificiosSeleccionados'] option[value='"+domEle+"']").attr("selected",true).change();
    });

    for (var i = 0; i < document.getElementById("lstSeleccionados").options.length; i++)
    {
        agregarNEM(document.getElementById("lstSeleccionados").options[i].text.substring(0,document.getElementById("lstSeleccionados").options[i].text.indexOf(" ")));
    }
//    $("#responsableC option[selected='selected']").attr("class","selected");
//    $("#responsableC").fcbkcomplete({
//        complete_text: "Comienza a escribir un nombre, si el nombre que buscas est&aacute; en la lista, seleccionalo, si no, al terminar de escribir presiona 'Enter'",
//        maxitems: 1,
//        height: 10,
//        onselect: function(){retrieveURL('/registroUniversidad.msut?ask=cboCaptura','registroUniversidadForm');},
//        newel: true
//    });
//    $('#responsableC').attr("name","respCaptura");
//    $('#mailBox1').on('blur', function() {
//        $(this).mailcheck({
//            suggested: function(element, suggestion) {
//                $('.email_suggestion1').html('Quiso decir: '+suggestion.address+'@'+'<strong>'+suggestion.domain+'</strong>?').slideDown();
//                sug1 = suggestion.full;
//            },
//            empty: function(element) {
//                $('.email_suggestion1').html('').slideUp();
//            }
//        });
//    });
//
//    $('#mailBox2').on('blur', function() {
//        $(this).mailcheck({
//            suggested: function(element, suggestion) {
//                $('.email_suggestion2').html('Quiso decir: '+suggestion.address+'@'+'<strong>'+suggestion.domain+'</strong>?').slideDown();
//                sug2 = suggestion.full;
//            },
//            empty: function(element) {
//                $('.email_suggestion2').html('').slideUp();
//            }
//        });
//    });
//
//    $('#mailBox3').on('blur', function() {
//        $(this).mailcheck({
//            suggested: function(element, suggestion) {
//                $('.email_suggestion3').html('Quiso decir: '+suggestion.address+'@'+'<strong>'+suggestion.domain+'</strong>?').slideDown();
//                sug3 = suggestion.full;
//            },
//            empty: function(element) {
//                $('.email_suggestion3').html('').slideUp();
//            }
//        });
//    });

    $('#add').click(function() {
        if($("#cboPE").val() > 0){
            selected = "";
            aniosInco = "";
            nuevoingreso=""
            matriculatotal="";
            clasificacion="";
            !$('#cboPE option:selected').appendTo('#lstSeleccionados');
            !$('#cboAnioInc option:selected').appendTo('#lstAniosInc');
            var clasi=$('#cboclasificacionPE option:selected').val().toString();
//            if(clasi == 0 || clasi == "TB" ){
//                clasi = "TB";
//            } if(clasi == 1 || clasi == "TN"){
//                clasi = "TN";
//            }
            $("<option value='"+clasi+"'>"+clasi+"</option>").appendTo('#lstClasificacion');
            var nuevo=  $("input[name='nuevo_ingresoPE']").val();
            $("<option value='"+nuevo+"'>"+nuevo+"</option>").appendTo('#lstNuevoIngreso');
            var matricula=  $("input[name='matricula_totalPE']").val();
            $("<option value='"+matricula+"'>"+matricula+"</option>").appendTo('#lstMatriculaTotal');
            
             for (var i = 0; i < document.getElementById("lstSeleccionados").options.length; i++){
                selected += document.getElementById("lstSeleccionados").options[ i ].value.toString() + ",";
                aniosInco += document.getElementById("lstAniosInc").options[ i ].value.toString()  + ",";
                nuevoingreso += document.getElementById("lstNuevoIngreso").options[ i ].value.toString()  + ",";
                matriculatotal += document.getElementById("lstMatriculaTotal").options[ i ].value.toString()  + ",";
                clasificacion += document.getElementById("lstClasificacion").options[ i ].value.toString()  + ",";
                
            }
            document.getElementById("lstSeleccionados").selectedIndex = -1;
            var prr = document.getElementById("lstSeleccionados").options.length -1;
            agregarNEM(document.getElementById("lstSeleccionados").options[prr].text.
                substring(0,document.getElementById("lstSeleccionados").options[ prr ].text.indexOf(" ")));
            $('#lstSeleccionados').each(function() {
                $(this).attr('size', $(this).find('option').length)
            });
            $( "#lstSeleccionados" ).effect( "highlight", {}, 1000, retrieveURL('/registroUniversidad.msut?ask=agregar&seleccionados='+selected+'&lstAniosInc='+aniosInco+'&lstNuevoIngreso='+nuevoingreso+'&lstMatriculaTotal='+matriculatotal+'&lstClasificacion='+clasificacion,'registroUniversidadForm'));
            $("input[name='nuevo_ingresoPE']").val("0");
             $("input[name='matricula_totalPE']").val("0");
            
        }else{
            $("#cboPE, [name='cboFamilia'], [name='cboArea']").effect( "highlight", {
                color:"#ffff55"
            } ,700);
            $().alerta("amarillo","Debes elegir un programa educativo para realizar esa acci&oacute;n");
        }
    });

    $("#remove").click(function() {
        if($("#lstSeleccionados").val() > 0){
            selected = "";
            aniosInco = "";
            nuevoingreso=""
            matriculatotal="";
            clasificacion="";
            borrarNEM($('#lstSeleccionados option:selected').text().substring(0,$('#lstSeleccionados option:selected').text().indexOf(" ")));
            !$('#lstSeleccionados option:selected').remove();
            !$('#lstAniosInc option:selected').remove();
            !$('#lstNuevoIngreso option:selected').remove();
            !$('#lstMatriculaTotal option:selected').remove();
            !$('#lstClasificacion option:selected').remove();
            for (var i = 0; i < document.getElementById("lstSeleccionados").options.length; i++){
                selected += document.getElementById("lstSeleccionados").options[ i ].value.toString() + ",";
                aniosInco += document.getElementById("lstAniosInc").options[ i ].value.toString()  + ",";
                nuevoingreso += document.getElementById("lstNuevoIngreso").options[ i ].value.toString()  + ",";
                matriculatotal += document.getElementById("lstMatriculaTotal").options[ i ].value.toString()  + ",";
                clasificacion += document.getElementById("lstClasificacion").options[ i ].value.toString()  + ",";
           }
            $('#lstSeleccionados').each(function() {
                $(this).attr('size', $(this).find('option').length)
            });
            retrieveURL('/registroUniversidad.msut?ask=agregar&seleccionados='+selected+'&lstAniosInc='+aniosInco+'&lstNuevoIngreso='+nuevoingreso+'&lstMatriculaTotal='+matriculatotal+'&lstClasificacion='+clasificacion,'registroUniversidadForm');
        }else{
            $("#lstSeleccionados").effect( "highlight", {
                color:"#ffff55"
            } ,700);
            $().alerta("amarillo","Debes elegir un programa educativo para realizar esa acci&oacute;n");
        }
    });

    $(".botonAgregar").button({
        icons: {
            primary: "ui-icon-plus"
        }
    });

    $(".botonQuitar").button({
        icons: {
            primary: "ui-icon-minus"
        }
    });
    
    $("div > form > div").accordion({
        icons: {
            "header": "ui-icon-arrowreturnthick-1-s",
            "headerSelected": "ui-icon-arrowreturnthick-1-n"
        },
        collapsible: true,
        autoHeight: false,
        navigation: true
    });

    $("input[name='fechaInicioC']").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat:"yy-mm-dd",
        onSelect: function( selectedDate ) {
            $( "input[name='fechaFinC']").datepicker( "option", "minDate", selectedDate );
        }
    });
    $("input[name='fechaFinC']").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat:"yy-mm-dd",
        onSelect: function( selectedDate ) {
            $( "input[name='fechaInicioC']").datepicker( "option", "maxDate", selectedDate );
        }
    });
    $("#certUni").click(function() {
        if($("#certUni").next().hasClass("ui-state-active")){
            $("input[name='certUni']").val(true);
        }else{
            $("input[name='certUni']").val(false);
        }
    });
    $("#orgInt").click(function() {
        if($("#orgInt").next().hasClass("ui-state-active")){
            $("input[name='orgInt']").val(true);
        }else{  
            $("input[name='orgInt']").val(false);
        }
    });
    if($("input[name='orgInt']").val() == "true"){
        $("#orgInt").trigger("click");
        $("input[name='orgInt']").val(true);
    }
    if($("input[name='certUni']").val() == "true"){
        $("#certUni").trigger("click");
        $("input[name='certUni']").val(true);
    }
    
    $("#nuevoOrgInt, #nuevaCertInd, input[type='checkbox']").button();
    $('#lstOrgInt, #lstCert, #lstSeleccionados').each(function() {
        $(this).attr('size', $(this).find('option').length)
    });
    $("#tabs").tabs();
   
});