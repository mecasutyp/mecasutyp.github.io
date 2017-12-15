/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var mensajes;

$(function(){
    
    recuperarMensajes();
    
    $("#leermensaje").dialog({
        autoOpen: false,
        width: 900,
        height: 700,
        modal: true,
        buttons: {
            "Responder": function() {
                if ($( "#asuntoM" ).val()==""){
                    $().alerta("rojo","Debe ingresar un asunto v&aacute;lido.");
                    return;
                }
                if ($( "#mensajeCompletoR" ).val()==""){
                    $().alerta("rojo","Debe ingresar un mensaje v&aacute;lido.");
                    return;
                }
                if ($( "#mensajeCompletoR" ).val().length<10){
                    $().alerta("rojo","El mensaje no puede ser menor a 10 car&aacute;cteres.");
                    return;
                }else{
                    enviarMensaje();
                    $( this ).dialog( "close" );
                }
            },
            "Cancelar": function() {
                $( this ).dialog( "close" );
            }
        },
        close: function(){
            recuperarMensajes();
        }
    });  
});

function recuperarMensajes(){
    $.ajax({
        url: "/mensajeriaNueva",
        type: "POST",
        data: {
            "ask" : "mensajesPreview"
        },
        beforeSend: function(){
            $("#tableMessages #cuerpo").html("");
        }
    }).done(function(msg) {
        if (msg.length>0){
            mensajes = msg.split("|");
            $.each(mensajes, function(index, elemento){
                var data = elemento.toString().split("^");
                /* [0]=id_remitente [1]=nombre_universidad [2]=asunto [3]=mensaje [4]=fecha [5]=estatus [6]=id_mensaje */
                var newElement="<tr><td>"+data[1]+"</td><td><a onclick='leerMensaje("+index+");' href='#'>"+data[2]+"</a></td>";
                if (data[5] == "NoLeido"){
                    newElement+="<td><strong>"+data[5]+"</strong></td>";
                }else{
                    newElement+="<td>"+data[5]+"</td>";
                }
                newElement+="<td>"+data[4]+"</td><td><a onclick='eliminarMensaje("+index+");' href='#'>X</a></td></tr>";
                $("#tableMessages #cuerpo").append(newElement);
            });
            
        }else{
            $("#tableMessages #cuerpo").append("<tr><td colspan='5'> <strong>No existen mensajes </strong> </td></tr>");
        }
    }).fail(function(msg){
        alert("fail " + msg);
    });
}

function leerMensaje(index){

    var data = mensajes[index].toString().split("^");
    $("#deUttec").html(data[1]);
    $("#asuntoM").val(data[2]);
    $("#mensajeCompleto").val(data[3]);
    $("#mensajeCompletoR").val("\n\n\n\n------------------ Mensaje Original ------------------\n\n" + data[3]);
    $("#idDest").val(data[0]);
    
    $("#leermensaje").dialog("open");
    
    $.ajax({
        url: "/mensajeriaNueva",
        type: "POST",
        data: {
            "ask" : "marcarLeido",
            "idMensaje" : data[6]
        }
    }).done(function(msg) {
        if (msg == "success"){
        //alert("marcado como leido");
        }else{
            alert("fatality");
        }
    }).fail(function(msg){
        alert("fail " + msg);
    });
}
function eliminarMensaje(index){

    var data = mensajes[index].toString().split("^");
    $.ajax({
        url: "/mensajeriaNueva",
        type: "POST",
        data: {
            "ask" : "eliminarmsj",
            "idMensaje" : data[6]
        }
    }).done(function(msg) {
        if (msg == "success"){
            location.reload(true);
        }else{
            alert("fatality");
        }
    }).fail(function(msg){
        alert("fail " + msg);
    });
}

function enviarMensaje(){
    $.ajax({
        url: "/mensajeriaNueva",
        type: "POST",
        data: {
            "ask" : "enviarMensaje",
            "asunto" : $("#asuntoM").val(),
            "mensaje" : $("#mensajeCompletoR").val(),
            "destinatario" : $("#idDest").val()
        }
    }).done(function(msg) {
        if (msg=="Error"){
            alert("Error");
        }else if (msg!=0){
            $().alerta("verde","Mensaje enviado correctamente.");
           
        }else{
            $().alerta("rojo","El mensaje no se ha enviado correctamente");
        }
    }).fail(function(msg){
        alert("fail " + msg);
    });
}