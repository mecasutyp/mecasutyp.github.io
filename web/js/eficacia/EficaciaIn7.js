function indicador7(control,cu,fi,save,dofi){
    if (!/^([0-9])*$/.test(control.value)){ //SI EL VALOR NO ES VÁLIDO
        if (control.value==""){
            control.value=0;
            return;
        }
        $().alerta("rojo","El valor '"+ control.value +"' no es v&aacute;lido");
        $(control).removeClass("inputok").addClass("inputerror");
        return;
    }else{
        $(control).removeClass("inputerror").addClass("inputok");
        control.value=Number(control.value);              
        if (!$("form[name='EficaciaIn7Form'] input").hasClass("inputerror")){
            operacionesEficacia7(cu,fi,save,dofi);
        }      
    }
    if ($('form[name="EficaciaIn7Form"] input').hasClass("inputerror")){
        $('li[class*="ui-tabs-selected ui-state-active"]').globo("update", "<center>Indicador con <br/> errores, favor <br/> de revisar.</center>").globo("show");
    }else{
        $('li[class*="ui-tabs-selected ui-state-active"]').globo("hide");
    }
}

function validarGuardado7(cu,fi,save,dofi){
    var c=$("form[name='EficaciaIn7Form'] input[name='fin']").val();
    var cadena = "-"; //GUARDA LOS VALORES EN UNA CADENA
    var error=false;
    if ($("form[name='EficaciaIn7Form'] input").hasClass("inputerror")){ //SI TIENE ERRORES
        //$().alerta("rojo","Error al guardar. Por favor verifique los datos");
        enviarDatosIndicadores("Error",'EficaciaIn7Datos','EficaciaIn7Form');
        return -1;
    }else{
        var temp;
        //Se evaluan la columna h con el total de matricula reportada por cada nivel educativo existente
        for(var j=1;j<c;j++){
            var niv=$("form[name='EficaciaIn7Form'] input[name='id_niv"+j+"']").val();
            for(var l=1;l<=$("form[name='EficaciaIn7Form'] input[name='preguntas_nivel"+j+"']").val();l++){
                if (l===1){
                    temp=parseInt($("form[name='EficaciaIn7Form'] input[id='total_h"+j+l+"']").val());
                }else{
                    if (temp != parseInt($("form[name='EficaciaIn7Form'] input[id='total_h"+j+l+"']").val()) ){
                        error=true;
                        $("form[name='EficaciaIn7Form'] input[id^='total_h"+j+"']").addClass("inputalert").removeClass("inputok");
                        $().alerta("amarillo","Datos NO guardados. Los totales de la columna 'H' del cuadro 7.1."+niv+" deben de coincidir en cada pregunta.");                      
                        return -1;//LOS TOTALES DE LA COLUMNA H NO COINCIDEN, SE DEVUELVE UN ERROR
                    }
                }
                if(parseInt($("form[name='EficaciaIn7Form'] input[id='total_h"+j+l+"']").val())<parseInt($("form[name='EficaciaIn7Form'] div[name='muestreo_nivel"+j+"']").html())){
                    error=true;      
                    $("form[name='EficaciaIn7Form'] input[id='total_h"+j+l+"']").addClass("inputalert").removeClass("inputok");
                    $().alerta("amarillo","Datos NO guardados. El total de las columnas 'H' debe ser al menos el muestro indicado '"+$("form[name='EficaciaIn7Form'] div[name='muestreo_nivel"+j+"']").html()+"' del cuadro 7.1."+niv+"");                      
                    return -1;
                }else{
                    $("form[name='EficaciaIn7Form'] input[id='total_h"+j+l+"']").addClass("inputok").removeClass("inputalert");
                    error=false;
                }
            }
        }
        if (error==false){//VALIDACION DE MUESTREO
            cadena= operacionesEficacia7(cu,fi,save,dofi);
            //$().alerta("verde","Datos Guardados Correctamente.");
             //SE GUARDARA LOS COMENTARIOS
                    var comentario = $("form[name='EficaciaIn7Form'] input[name='comentario']").val();
                     if(comentario != "Sin comentarios"){
                         var indicador = "7";
                         GuardarComentarios(comentario, indicador);
                     }
            return cadena;
        }else{
            return -1;
        }
    }
}

function operacionesEficacia7(cu,fi,save,dofi){
    if(parseInt(save)==0){      
        var col_A=0,col_B=0,col_C=0,col_D=0,col_E=0,col_F=0,col_G=0,col_H=0, col_H_tes=0,col_I=0,col_J=0,col_K5=0,col_K10=0, num_Preguntas=0;
        for(var i=1; i<$("form[name='EficaciaIn7Form'] input[name='no_fil_niv"+cu+"']").val(); i++){             
            if(dofi==0){
                $("form[name='EficaciaIn7Form'] input[name='th1_"+cu+"_"+i+"']").val(parseInt($("form[name='EficaciaIn7Form'] input[name='val_A_"+cu+"_"+i+"']").val())+
                    parseInt($("form[name='EficaciaIn7Form'] input[name='val_B_"+cu+"_"+i+"']").val())+ parseInt($("form[name='EficaciaIn7Form'] input[name='val_C_"+cu+"_"+i+"']").val())+
                    parseInt($("form[name='EficaciaIn7Form'] input[name='val_D_"+cu+"_"+i+"']").val())+ parseInt($("form[name='EficaciaIn7Form'] input[name='val_E_"+cu+"_"+i+"']").val())+
                    parseInt($("form[name='EficaciaIn7Form'] input[name='val_F_"+cu+"_"+i+"']").val())+ parseInt($("form[name='EficaciaIn7Form'] input[name='val_G_"+cu+"_"+i+"']").val()));
        
                $("form[name='EficaciaIn7Form'] input[name='th2_"+cu+"_"+i+"']").val(parseInt($("form[name='EficaciaIn7Form'] input[name='val_A_"+cu+"_"+i+"']").val())+
                    parseInt($("form[name='EficaciaIn7Form'] input[name='val_B_"+cu+"_"+i+"']").val())+ parseInt($("form[name='EficaciaIn7Form'] input[name='val_C_"+cu+"_"+i+"']").val())+
                    parseInt($("form[name='EficaciaIn7Form'] input[name='val_D_"+cu+"_"+i+"']").val())+ parseInt($("form[name='EficaciaIn7Form'] input[name='val_E_"+cu+"_"+i+"']").val()));
        
                $("form[name='EficaciaIn7Form'] input[name='th3_"+cu+"_"+i+"']").val(parseInt($("form[name='EficaciaIn7Form'] input[name='val_A_"+cu+"_"+i+"']").val())*5+
                    parseInt($("form[name='EficaciaIn7Form'] input[name='val_B_"+cu+"_"+i+"']").val()) * 4 + parseInt($("form[name='EficaciaIn7Form'] input[name='val_C_"+cu+"_"+i+"']").val())*3+
                    parseInt($("form[name='EficaciaIn7Form'] input[name='val_D_"+cu+"_"+i+"']").val()) * 2 + parseInt($("form[name='EficaciaIn7Form'] input[name='val_E_"+cu+"_"+i+"']").val())*1);
            
                if(parseInt($("form[name='EficaciaIn7Form'] input[name='th2_"+cu+"_"+i+"']").val())==0){               
                    $("form[name='EficaciaIn7Form'] input[name='th4_"+cu+"_"+i+"']").val(0);
                } else{
                    $("form[name='EficaciaIn7Form'] input[name='th4_"+cu+"_"+i+"']").val(getDecimal((parseInt($("form[name='EficaciaIn7Form'] input[name='th3_"+cu+"_"+i+"']").val())/parseInt($("form[name='EficaciaIn7Form'] input[name='th2_"+cu+"_"+i+"']").val()))));
                }       
                $("form[name='EficaciaIn7Form'] input[name='th5_"+cu+"_"+i+"']").val(getDecimal((parseFloat($("form[name='EficaciaIn7Form'] input[name='th4_"+cu+"_"+i+"']").val())*2)));      
            }
            if(dofi==1){
                $("form[name='EficaciaIn7Form'] input[name='th1_"+cu+"_"+fi+"']").val(parseInt($("form[name='EficaciaIn7Form'] input[name='val_A_"+cu+"_"+fi+"']").val())+
                    parseInt($("form[name='EficaciaIn7Form'] input[name='val_B_"+cu+"_"+fi+"']").val())+ parseInt($("form[name='EficaciaIn7Form'] input[name='val_C_"+cu+"_"+fi+"']").val())+
                    parseInt($("form[name='EficaciaIn7Form'] input[name='val_D_"+cu+"_"+fi+"']").val())+ parseInt($("form[name='EficaciaIn7Form'] input[name='val_E_"+cu+"_"+fi+"']").val())+
                    parseInt($("form[name='EficaciaIn7Form'] input[name='val_F_"+cu+"_"+fi+"']").val())+ parseInt($("form[name='EficaciaIn7Form'] input[name='val_G_"+cu+"_"+fi+"']").val()));
        
                $("form[name='EficaciaIn7Form'] input[name='th2_"+cu+"_"+fi+"']").val(parseInt($("form[name='EficaciaIn7Form'] input[name='val_A_"+cu+"_"+fi+"']").val())+
                    parseInt($("form[name='EficaciaIn7Form'] input[name='val_B_"+cu+"_"+fi+"']").val())+ parseInt($("form[name='EficaciaIn7Form'] input[name='val_C_"+cu+"_"+fi+"']").val())+
                    parseInt($("form[name='EficaciaIn7Form'] input[name='val_D_"+cu+"_"+fi+"']").val())+ parseInt($("form[name='EficaciaIn7Form'] input[name='val_E_"+cu+"_"+fi+"']").val()));
        
                $("form[name='EficaciaIn7Form'] input[name='th3_"+cu+"_"+fi+"']").val(parseInt($("form[name='EficaciaIn7Form'] input[name='val_A_"+cu+"_"+fi+"']").val())*5+
                    parseInt($("form[name='EficaciaIn7Form'] input[name='val_B_"+cu+"_"+fi+"']").val()) * 4 + parseInt($("form[name='EficaciaIn7Form'] input[name='val_C_"+cu+"_"+fi+"']").val())*3+
                    parseInt($("form[name='EficaciaIn7Form'] input[name='val_D_"+cu+"_"+fi+"']").val()) * 2 + parseInt($("form[name='EficaciaIn7Form'] input[name='val_E_"+cu+"_"+fi+"']").val())*1);
            
                if(parseInt($("form[name='EficaciaIn7Form'] input[name='th2_"+cu+"_"+fi+"']").val())==0){                   
                    $("form[name='EficaciaIn7Form'] input[name='th4_"+cu+"_"+fi+"']").val(0);
                              
                } else{
                    $("form[name='EficaciaIn7Form'] input[name='th4_"+cu+"_"+fi+"']").val(getDecimal((parseInt($("form[name='EficaciaIn7Form'] input[name='th3_"+cu+"_"+fi+"']").val())/parseInt($("form[name='EficaciaIn7Form'] input[name='th2_"+cu+"_"+fi+"']").val()))));
                }
       
                $("form[name='EficaciaIn7Form'] input[name='th5_"+cu+"_"+fi+"']").val(getDecimal((parseFloat($("form[name='EficaciaIn7Form'] input[name='th4_"+cu+"_"+fi+"']").val())*2)));               
            }     
            col_A+=parseInt($("form[name='EficaciaIn7Form'] input[name='val_A_"+cu+"_"+i+"']").val()),col_B+=parseInt($("form[name='EficaciaIn7Form'] input[name='val_B_"+cu+"_"+i+"']").val());
            col_C+=parseInt($("form[name='EficaciaIn7Form'] input[name='val_C_"+cu+"_"+i+"']").val()),col_D+=parseInt($("form[name='EficaciaIn7Form'] input[name='val_D_"+cu+"_"+i+"']").val());
            col_E+=parseInt($("form[name='EficaciaIn7Form'] input[name='val_E_"+cu+"_"+i+"']").val()),col_F+=parseInt($("form[name='EficaciaIn7Form'] input[name='val_F_"+cu+"_"+i+"']").val());
            col_G+=parseInt($("form[name='EficaciaIn7Form'] input[name='val_G_"+cu+"_"+i+"']").val()),col_H+=parseInt($("form[name='EficaciaIn7Form'] input[name='th1_"+cu+"_"+i+"']").val());
            col_I+=parseInt($("form[name='EficaciaIn7Form'] input[name='th2_"+cu+"_"+i+"']").val()),col_J+=parseInt($("form[name='EficaciaIn7Form'] input[name='th3_"+cu+"_"+i+"']").val());
            col_K5+=parseFloat($("form[name='EficaciaIn7Form'] input[name='th4_"+cu+"_"+i+"']").val()),col_K10+=parseFloat($("form[name='EficaciaIn7Form'] input[name='th5_"+cu+"_"+i+"']").val());              
           
        }        
        $("form[name='EficaciaIn7Form'] input[name='tv_"+cu+"-1']").val(col_A),$("form[name='EficaciaIn7Form'] input[name='tv_"+cu+"-2']").val(col_B),
        $("form[name='EficaciaIn7Form'] input[name='tv_"+cu+"-3']").val(col_C),$("form[name='EficaciaIn7Form'] input[name='tv_"+cu+"-4']").val(col_D),
        $("form[name='EficaciaIn7Form'] input[name='tv_"+cu+"-5']").val(col_E),$("form[name='EficaciaIn7Form'] input[name='tv_"+cu+"-6']").val(col_F),
        $("form[name='EficaciaIn7Form'] input[name='tv_"+cu+"-7']").val(col_G),$("form[name='EficaciaIn7Form'] input[name='tv_"+cu+"-8']").val(col_H);
        $("form[name='EficaciaIn7Form'] input[name='tv_"+cu+"-9']").val(col_I),$("form[name='EficaciaIn7Form'] input[name='tv_"+cu+"-10']").val(col_J);
        
        num_Preguntas= parseInt($("form[name='EficaciaIn7Form'] input[name='totalpreguntas']").val());
        
        //DISTRIBUCION PORCENTUAL
        var col_A_D=0, col_B_D=0, col_C_D=0, col_D_D=0, col_E_D=0, col_F_D=0, col_G_D=0, col_I_D=0.0, col_H_TES=0;
        col_A_D=getDecimal((col_A/col_H)*100);
        col_B_D=getDecimal((col_B/col_H)*100);
        col_C_D=getDecimal((col_C/col_H)*100);
        col_D_D=getDecimal((col_D/col_H)*100);
        col_E_D=getDecimal((col_E/col_H)*100);
        col_F_D=getDecimal((col_F/col_H)*100);
        col_G_D=getDecimal((col_G/col_H)*100);
        if(isNaN(col_A_D)){ col_A_D= "0 %"; }else{col_A_D= col_A_D+" %";}//SI EL RESULTADO ES NAN
        if(isNaN(col_B_D)){ col_B_D= "0 %"; }else{col_B_D= col_B_D+" %";}
        if(isNaN(col_C_D)){ col_C_D= "0 %"; }else{col_C_D= col_C_D+" %";}
        if(isNaN(col_D_D)){ col_D_D= "0 %"; }else{col_D_D= col_D_D+" %";}
        if(isNaN(col_E_D)){ col_E_D= "0 %"; }else{col_E_D= col_E_D+" %";}
        if(isNaN(col_F_D)){ col_F_D= "0 %"; }else{col_F_D= col_F_D+" %";}
        if(isNaN(col_G_D)){ col_G_D= "0 %"; }else{col_G_D= col_G_D+" %";}
        
        col_H_TES=parseInt($("form[name='EficaciaIn7Form'] input[name='th1_"+cu+"_"+num_Preguntas+"']").val());
        col_I_D=getDecimal( (((col_A+col_B) / num_Preguntas)/ col_H_TES)*100  );
        if(col_I_D == Number.POSITIVE_INFINITY || col_I_D == Number.NEGATIVE_INFINITY){//ES RESULTADO INFINITO
            col_I_D="";
        }else{
            if(isNaN(col_I_D)){ 
                col_I_D= ""; 
            }else{
                col_I_D= col_I_D+" %";
            }
        }
        $("form[name='EficaciaIn7Form'] input[name='tv_"+cu+"-1-d-ms']").val(col_A_D), $("form[name='EficaciaIn7Form'] input[name='tv_"+cu+"-2-d-s']").val(col_B_D),
        $("form[name='EficaciaIn7Form'] input[name='tv_"+cu+"-3-d-rs']").val(col_C_D), $("form[name='EficaciaIn7Form'] input[name='tv_"+cu+"-4-d-ps']").val(col_D_D),
        $("form[name='EficaciaIn7Form'] input[name='tv_"+cu+"-5-d-ns']").val(col_E_D), $("form[name='EficaciaIn7Form'] input[name='tv_"+cu+"-6-d-na']").val(col_F_D),
        $("form[name='EficaciaIn7Form'] input[name='tv_"+cu+"-7-d-ne']").val(col_G_D), $("form[name='EficaciaIn7Form'] input[name='tv_"+cu+"-9-d-tes']").val(col_I_D);
          
        
        
        if(parseInt(col_I)==0){
            $("form[name='EficaciaIn7Form'] input[name='tv_"+cu+"-11']").val(0),$("form[name='EficaciaIn7Form'] input[name='tv_"+cu+"-12']").val(0);             
        }
        else{
            $("form[name='EficaciaIn7Form'] input[name='tv_"+cu+"-11']").val(getDecimal((col_J/col_I))),$("form[name='EficaciaIn7Form'] input[name='tv_"+cu+"-12']").val(getDecimal((parseFloat($("form[name='EficaciaIn7Form'] input[name='tv_"+cu+"-11']").val())*2)));             
        }
        col_A=0,col_B=0,col_C=0,col_D=0,col_E=0,col_F=0,col_G=0,col_H=0,col_I=0,col_J=0,col_K5=0,col_K10=0;
    }
    if(parseInt(save)==1){  
        var cadena="-";
        for(var k=1;k<cu;k++){
            for(var l=1;l<$("form[name='EficaciaIn7Form'] input[name='no_fil_niv"+k+"']").val();l++){
                //cadena=cadena.concat($("form[name='EficaciaIn7Form'] input[name='id_niv"+k+"']").val(),",");
                cadena=cadena.concat($("form[name='EficaciaIn7Form'] input[name='id_preg"+k+"_"+l+"']").val(),","),cadena=cadena.concat($("form[name='EficaciaIn7Form'] input[name='val_A_"+k+"_"+l+"']").val(),",");
                cadena=cadena.concat($("form[name='EficaciaIn7Form'] input[name='val_B_"+k+"_"+l+"']").val(),","),cadena=cadena.concat($("form[name='EficaciaIn7Form'] input[name='val_C_"+k+"_"+l+"']").val(),",");
                cadena=cadena.concat($("form[name='EficaciaIn7Form'] input[name='val_D_"+k+"_"+l+"']").val(),","),cadena=cadena.concat($("form[name='EficaciaIn7Form'] input[name='val_E_"+k+"_"+l+"']").val(),",");
                cadena=cadena.concat($("form[name='EficaciaIn7Form'] input[name='val_F_"+k+"_"+l+"']").val(),","),cadena=cadena.concat($("form[name='EficaciaIn7Form'] input[name='val_G_"+k+"_"+l+"']").val(),"-");
            }      
        }
        enviarDatosIndicadores(cadena,'EficaciaIn7Datos','EficaciaIn7Form');
    }  
   
    return cadena;
}
function blurVal7(){
    $('form[name="EficaciaIn7Form"] input[name^="no_fil_niv"]').each(function(index, domEle) {
        $(domEle).blur();       
    });
}

