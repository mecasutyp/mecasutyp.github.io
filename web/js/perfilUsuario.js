function guardarUsuario(){
    if($("input[name='passNuevo']").val() != $("input[name='nuevoPassword']").val()){
        $().alerta("amarillo","Las contrase&ntilde;as no coinciden");
        $("input[name='nuevoPassword'][name='passNuevo']").val("");
    }else if($("input[name='password']").val().length < 4){
        $().alerta("amarillo","Introduce la contrase&ntilde;a actual");
        $("input[name='nuevoPassword'][name='passNuevo'][name='password']").val("");
    }else if(!/(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,15})$/.test($("input[name='nuevoPassword']").val())){
        $().alerta("amarillo","La contrase&ntilde;a nueva debe ser de 6 a 15 caracteres por lo menos un digito y un alfanum&eacute;rico, y no puede contener caracteres especiales");
        $("input[name='nuevoPassword'][name='passNuevo']").val("");
    }else{
        //retrieveURL('/Perfil.msut?ask=guardarUsuario&idUser='+idUser,'PerfilForm');
        $("input[name='ask']").val("guardarUsuario");
        $("input[name='idUser']").val(idUser);
        $("form:first").trigger("submit");
        //$("input[name='nuevoPassword'],input[name='passNuevo'],input[name='password'],input[name='user']").val("");
        //idUser = "x";
        //$().alerta("verde","Datos guardados correctamente");
    }
}

$(document).ready(function(){
    $("#iniEv").remove();
    $("div > form > div").accordion({
        icons: {
            "header": "ui-icon-arrowreturnthick-1-s", 
            "headerSelected": "ui-icon-arrowreturnthick-1-n"
        },
        collapsible: true,
        autoHeight: false,
        navigation: true
    });
    
    $("select[name='id_usuario']").change(function(){
        idUser = $(this).val();
    });
    
    $("input[name='nombre'],input[name='apaterno'],input[name='amaterno']").attr("disabled",true);
    
    $("form a").button();
});