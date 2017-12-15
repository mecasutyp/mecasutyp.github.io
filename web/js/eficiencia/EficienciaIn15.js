//    Document   : Script Eficiencia15
//    Author     : Daniel Ramìrez Torres
//    Co-author     : Salvador Zamora   22/09/2016
function Eficiencia15Radios(){
    var cadena0="z";
    var nocuadros=$("form[name='EficienciaIn15Form'] input[name='nocuadros']").val();
    for(var k=1;k<=nocuadros;k++){
        if (k!=2){
            cadena0=cadena0.concat($("form[name='EficienciaIn15Form'] input[name='id_certificado"+k+"']").val(),",");
            for (var i=0;i<$("form[name='EficienciaIn15Form'] input[name='radioss"+k+"']").length;i++){            
                if ($("form[name='EficienciaIn15Form'] input[name='radioss"+k+"']")[i].checked==true){
                    cadena0+="1,";
                }
                if ($("form[name='EficienciaIn15Form'] input[name='radioss"+k+"']")[i].checked==false){
                    cadena0+="0,";
                }
            }
            cadena0=cadena0.concat("z");        
        }
    }
    return(cadena0);
}

function Eficiencia15RadiosB(){
    var cadena0="";
    var nocuadrosb=$("form[name='EficienciaIn15Form'] input[name='nocuadrosb']").val();
       for(var k=1;k<=nocuadrosb;k++){        
        cadena0=cadena0.concat($("form[name='EficienciaIn15Form'] input[name='id_certificadob"+k+"']").val(),",");
        for (var i=0;i<$("form[name='EficienciaIn15Form'] input[name='option"+k+"']").length;i++){
            if ($("form[name='EficienciaIn15Form'] input[name='option"+k+"']")[i].checked==true){
                cadena0+="1,";
            }
            if ($("form[name='EficienciaIn15Form'] input[name='option"+k+"']")[i].checked==false){
                cadena0+="0,";
            }
        }
        cadena0=cadena0.concat("z");
    }
    return(cadena0);
}





function Eficiencia15Radios1(){
    var cadena="x";
    var total=0;
    for(var j=1;j<6;j++){
        cadena=cadena.concat(j,",");
        for (var i=0;i<$("form[name='EficienciaIn15Form'] input[name='radio2"+j+"']").length;i++){
            if ($("form[name='EficienciaIn15Form'] input[name='radio2"+j+"']")[i].checked){
                cadena=cadena.concat($("form[name='EficienciaIn15Form'] input[name='radio2"+j+"']")[i].value,",");              
                if($("form[name='EficienciaIn15Form'] input[name='radio2"+j+"']")[i].value==1){
                    $("form[name='EficienciaIn15Form'] input[name='fila1"+j+"']").removeAttr("disabled");
                    $("form[name='EficienciaIn15Form'] input[name='fila2"+j+"']").removeAttr("disabled");
                    $("form[name='EficienciaIn15Form'] input[name='fila3"+j+"']").removeAttr("disabled");
                    total=parseInt(total)+parseInt($("form[name='EficienciaIn15Form'] input[name='radio2"+j+"']")[i].value);
                }
                else{
                    $("form[name='EficienciaIn15Form'] input[name='fila1"+j+"']").attr("disabled","disabled");
                    $("form[name='EficienciaIn15Form'] input[name='fila2"+j+"']").attr("disabled","disabled");
                    $("form[name='EficienciaIn15Form'] input[name='fila3"+j+"']").attr("disabled","disabled");
                    $("form[name='EficienciaIn15Form'] input[name='fila1"+j+"']").val("0000-00-00");
                    $("form[name='EficienciaIn15Form'] input[name='fila2"+j+"']").val("0000-00-00");
                    $("form[name='EficienciaIn15Form'] input[name='fila3"+j+"']").val("0000-00-00");
                    $("form[name='EficienciaIn15Form'] input[name='fila1"+j+"']").removeClass("inputerror").addClass("inputok");
                     $("form[name='EficienciaIn15Form'] input[name='fila2"+j+"']").removeClass("inputerror").addClass("inputok");
                    total=parseInt(total)+parseInt($("form[name='EficienciaIn15Form'] input[name='radio2"+j+"']")[i].value);
                }
            //                for(var l=1;l<=3;l++){
            //                    if($("form[name='EficienciaIn15Form'] input[name='radio2"+j+"']")[i].value==1 && ($("form[name='EficienciaIn15Form'] input[name='fila"+l+j+"']").val()=="" || $("form[name='EficienciaIn15Form'] input[name='fila"+l+j+"']").val()=="0000-00-00")){
            //                        $("form[name='EficienciaIn15Form'] input[name='fila"+l+j+"']").globo("enable").globo("update", "<center>Elija una fecha v&aacute;lida<br/></center>").globo("show").removeClass("inputok").addClass("inputerror");
            //                    //                  Validar($("form[name='EficienciaIn15Form'] input[name='fila"+l+j+"']").val());
            //                    }
            //                    else{
            //                        $("form[name='EficienciaIn15Form'] input[name='fila"+l+j+"']").globo("disable").removeClass("inputerror").addClass("inputok");
            //                    }
            //                }
            }
        }
        for(var k=1;k<=3;k++){
            if($("form[name='EficienciaIn15Form'] input[name='fila"+k+j+"']").val()=="0000-00-00" || $("form[name='EficienciaIn15Form'] input[name='fila"+k+j+"']").val()==""){
                cadena=cadena.concat("NULL",",");
                $("form[name='EficienciaIn15Form'] input[name='fila"+k+j+"']").val("0000-00-00");
                
            }else{
                cadena=cadena.concat("'",$("form[name='EficienciaIn15Form'] input[name='fila"+k+j+"']").val(),"',");
            }
        }
        cadena =cadena.concat("x");
        $("form[name='EficienciaIn15Form'] input[name='total_radios']").val(total);
    }
    // alert(cadena);
    return(cadena);
}
    
function guardarEficiencia15(){
        
    var x1="";
    var x2="x";
    var x3="";
    var cadena="";
    verificarfechprocesos();
     var reemplazarcadena=false;
    var existearchivo=verificararchivo("EficienciaIn15Form");
     if ($("form[name='EficienciaIn15Form'] input").hasClass("inputerror")){
        $().alerta('rojo', 'Error al guardar. Por favor verifique los datos.');
        cadena="Error";
    }else{
        var tamanomaximo=2097152;
        var seenvia=true;
        if(existearchivo){
            if($("form[name='EficienciaIn15Form'] input[name='archivo']")[0].files[0].size > tamanomaximo){
                $().alerta('rojo', 'Error al guardar. El tama&ntilde;o del archivo es mayor al permitido.');
                  $("form[name='EficienciaIn15Form'] input[name='archivo']").removeClass("inputok").addClass("inputerror");
                  seenvia=false;
            }else{   
                var filexext=$("form[name='EficienciaIn15Form'] input[name='archivo']")[0].value;
                var extension = (filexext.substring(filexext.lastIndexOf("."))).toLowerCase(); 
                 var base64txt="";
                base64( $("form[name='EficienciaIn15Form'] input[name='archivo']")[0], function(data){
                    base64txt=data.base64;
                    var universidad=$("form[name='EficienciaIn15Form'] input[name='nomuni']").val();  
                    var IdUni=$("form[name='EficienciaIn15Form'] input[name='IdUni']").val();  
                    var IdPer=$("form[name='EficienciaIn15Form'] input[name='IdPer']").val();  
                    if(parseInt(data.size) < tamanomaximo){//SI ES MENOR A 2 MB (2097152 BYTES) SE ENVIARA
                         enviarcorreo(base64txt, universidad, "15", extension, IdUni, IdPer);
                    }
                }) 
            }   
        }else{
            $().alerta('rojo', 'Error al guardar. Es necesario enviar un archivo.');
            seenvia=false;
        }
        
        if(seenvia){
            x1 = Eficiencia15Radios();
            x2=Eficiencia15RadiosB();
            x3 = Eficiencia15Radios1();    
           cadena=x1+x2+x3;
           //SE GUARDARA LOS COMENTARIOS
           var comentario = $("form[name='EficienciaIn15Form'] input[name='comentario']").val();
            if(comentario != "Sin comentarios"){
                var indicador = "15";
                GuardarComentarios(comentario, indicador);
            }
        }else{
            cadena="Error";
        }
       
        //$().alerta('verde', 'Datos guardados correctamente.');
    }   
    return cadena;
}



/*VERIFICA QUE SE SELECCIONE EL PERIODO DE EVALUACIÓN*/
function verificarfechprocesos(){
     var malfecha=false;
     for(var j=1;j<6;j++){
          for (var i=0;i<$("form[name='EficienciaIn15Form'] input[name='radio2"+j+"']").length;i++){
            if ($("form[name='EficienciaIn15Form'] input[name='radio2"+j+"']")[i].checked){
                 if($("form[name='EficienciaIn15Form'] input[name='radio2"+j+"']")[i].value==1){
                    
                     if($("form[name='EficienciaIn15Form'] input[name='fila1"+j+"']").val()=="0000-00-00"){
                            malfecha=true;   
                            $("form[name='EficienciaIn15Form'] input[name='fila1"+j+"']").removeClass("inputok").addClass("inputerror");
                     }else{
                          $("form[name='EficienciaIn15Form'] input[name='fila1"+j+"']").removeClass("inputerror").addClass("inputok");
                     }if($("form[name='EficienciaIn15Form'] input[name='fila2"+j+"']").val()=="0000-00-00"){
                           malfecha=true;
                            $("form[name='EficienciaIn15Form'] input[name='fila2"+j+"']").removeClass("inputok").addClass("inputerror");
                     }else{
                          $("form[name='EficienciaIn15Form'] input[name='fila2"+j+"']").removeClass("inputerror").addClass("inputok");
                     }
               }
            }
          }  
     }
     if(malfecha){
        $().alerta("rojo","Ingresa el periodo que comprende la certificaci&oacute;n o recertificaci&oacute;n");
    }
}