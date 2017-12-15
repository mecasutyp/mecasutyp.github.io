var validar=false;
function indicador4(control,cu,save,des){
    var controles = new Array();
    var j = 0;
    while(document.forms["EficaciaIn4Form"][j].name != "fin"){
        controles.push(document.forms["EficaciaIn4Form"][j].name);
        j++;
    }    
    if (control.value == "")
        control.value = 0;
    if (!/^([0-9])*$/.test(control.value)){
        $().alerta("rojo","El valor '" + control.value + "' no es v&aacute;lido, debe ser un n&uacute;mero entero.");
        $(control).addClass("inputerror").removeClass("inputok");
        return;
    }else{
        $(control).val(Number(control.value));
        $(control).addClass("inputok").removeClass("inputerror");
        /*Modificar a partir de acá*/
        operacionesEficacia4(cu,save,des);
    }
}
 var sumtot =0
function operacionesEficacia4(c,save,des){
    var cadena = "",prom=0, cu=$("form[name='EficaciaIn4Form'] input[name=no_niv]").val(),cadena2="x";
    var num_fil=$("form[name='EficaciaIn4Form'] input[name=no_fil_niv]").val();       
        var validar =true;
        sumtot = 0;
       
    if(save==0){//SE CALCULARAN TOTALES
        var ssep_d=0,tv_1=0,tv_2=0,tv_3=0,cont=0;
        for(var i=1;i<num_fil;i++){            
            ssep_d=parseInt($("form[name='EficaciaIn4Form'] input[id=sep_dic_"+c+"_"+i+"]").val())+parseInt($("form[name='EficaciaIn4Form'] input[id=ene_abr_"+c+"_"+i+"]").val())
            +parseInt($("form[name='EficaciaIn4Form'] input[id=may_ago_"+c+"_"+i+"]").val());           
            $("form[name='EficaciaIn4Form'] input[name=th"+c+"_"+i+"]").val(ssep_d);        
            tv_1+=parseInt($("form[name='EficaciaIn4Form'] input[id=sep_dic_"+c+"_"+i+"]").val());        
            tv_2+=parseInt($("form[name='EficaciaIn4Form'] input[id=ene_abr_"+c+"_"+i+"]").val());        
            tv_3+=parseInt($("form[name='EficaciaIn4Form'] input[id=may_ago_"+c+"_"+i+"]").val());         
            $("form[name='EficaciaIn4Form'] input[name=ttv"+c+"]").val(tv_1+tv_2+tv_3);
        }
        $("form[name='EficaciaIn4Form'] input[name=tv"+c+"_1]").val(tv_1);    
        $("form[name='EficaciaIn4Form'] input[name=tv"+c+"_2]").val(tv_2);
        $("form[name='EficaciaIn4Form'] input[name=tv"+c+"_3]").val(tv_3);
        ssep_d=0,tv_1=0,tv_2=0,tv_3=0; 
       
        for(var k=1;k<cu;k++){
             var num_prog=$("form[name='EficaciaIn4Form'] input[name=num_prog"+k+"]").val();  
             var totalcol =[];
             for(var a=0;a<6;a++){ 
                 totalcol[a]=0;
             }
             for(var z=1;z<num_prog;z++){ 
                totalcol[0]+=parseInt($("form[name='EficaciaIn4Form'] input[name=tot_dessep"+k+"_"+z+"]").val());
                totalcol[2]+=parseInt($("form[name='EficaciaIn4Form'] input[name=tot_desene"+k+"_"+z+"]").val());
                totalcol[4]+=parseInt($("form[name='EficaciaIn4Form'] input[name=tot_desmay"+k+"_"+z+"]").val());
                totalcol[1]+=parseInt($("form[name='EficaciaIn4Form'] input[name=matSep"+k+"-"+z+"]").val());
                totalcol[3]+=parseInt($("form[name='EficaciaIn4Form'] input[name=matEne"+k+"-"+z+"]").val());
                totalcol[5]+=parseInt($("form[name='EficaciaIn4Form'] input[name=matMay"+k+"-"+z+"]").val());
            }
             for(var m=0;m<6;m++){ 
                $("form[name='EficaciaIn4Form'] input[name='totalcol,"+k+"-"+m+"']").val(totalcol[m]);
            }
            var re1 = 0;
            var re2 = 0;
            var re3 = 0;
            if(totalcol[0] > 0){
                re1 = getDecimal(totalcol[0]/totalcol[1]*100);
            }
            if(totalcol[2] > 0){
                re2 = getDecimal(totalcol[2]/totalcol[3]*100);
            }
            if(totalcol[4] > 0){
                re3 = getDecimal(totalcol[4]/totalcol[5]*100);
            }
            var ret = getDecimal((re1+re2+re3)/3);
             $("form[name='EficaciaIn4Form'] input[name='Div"+k+"_1']").val(re1 +" %");
             $("form[name='EficaciaIn4Form'] input[name='Div"+k+"_2']").val(re2 +" %");
             $("form[name='EficaciaIn4Form'] input[name='Div"+k+"_3']").val(re3 +" %");
             $("form[name='EficaciaIn4Form'] input[name='promD"+k+"']").val( ret + " %");
             sumtot+=ret;
        }   
        
       
        for(var k=1;k<cu;k++){
             var num_prog=$("form[name='EficaciaIn4Form'] input[name=num_prog"+k+"]").val();  
            for(var z=1;z<num_prog;z++){ 
               if(parseInt($("form[name='EficaciaIn4Form'] input[name='tot_dessep"+k+"_"+z+"']").val())> parseInt($("form[name='EficaciaIn4Form'] input[name='matSep"+k+"-"+z+"']").val())){
                    $("form[name='EficaciaIn4Form'] input[name='tot_dessep"+k+"_"+z+"']").globo("enable").globo("update",  "<center>No puede ser mayor el <br/>n&uacute;mero desertores, al de la<br/>Matric&uacute;la inicial atendida<br/>cuatrimestre Septiembre-Diciembre</center>").removeClass("inputok").addClass("inputerror");
                    validar = false;
                }else{
                    $("form[name='EficaciaIn4Form'] input[name='tot_dessep"+k+"_"+z+"']").globo("disable").removeClass("inputerror").addClass("inputok")   
                }
                if(parseInt($("form[name='EficaciaIn4Form'] input[name='tot_desene"+k+"_"+z+"']").val())> parseInt($("form[name='EficaciaIn4Form'] input[name='matEne"+k+"-"+z+"']").val())){
                    $("form[name='EficaciaIn4Form'] input[name='tot_desene"+k+"_"+z+"']").globo("enable").globo("update",  "<center>No puede ser mayor el <br/>n&uacute;mero desertores, al de la<br/>Matric&uacute;la inicial atendida<br/>cuatrimestre Enero-Abril</center>").removeClass("inputok").addClass("inputerror");
                    validar = false;
                }else{
                    $("form[name='EficaciaIn4Form'] input[name='tot_desene"+k+"_"+z+"']").globo("disable").removeClass("inputerror").addClass("inputok")   
                }
                if(parseInt($("form[name='EficaciaIn4Form'] input[name='tot_desmay"+k+"_"+z+"']").val())> parseInt($("form[name='EficaciaIn4Form'] input[name='matMay"+k+"-"+z+"']").val())){
                    $("form[name='EficaciaIn4Form'] input[name='tot_desmay"+k+"_"+z+"']").globo("enable").globo("update",  "<center>No puede ser mayor el <br/>n&uacute;mero desertores, al de la<br/>Matric&uacute;la inicial atendida<br/>cuatrimestre Mayo-Agosto</center>").removeClass("inputok").addClass("inputerror");
                    validar = false;
                }else{
                    $("form[name='EficaciaIn4Form'] input[name='tot_desmay"+k+"_"+z+"']").globo("disable").removeClass("inputerror").addClass("inputok")   
                }
        
            }
            
          
        }
        
    }
    if(save==1 && c==0){         
        if ($("form[name='EficaciaIn4Form'] input").hasClass("inputerror")){ //SI TIENE ERRORES
           $().alerta("rojo","Error al guardar. Por favor verifique los datos");                 
        }else{
            cadena+="";
            for(var k=1;k<cu;k++){        
                var num_prog=$("form[name='EficaciaIn4Form'] input[name=num_prog"+k+"]").val();  
                cadena+="";
                for(var l=1;l<num_fil;l++){ 
                    cadena=cadena.concat($("form[name='EficaciaIn4Form'] input[name=id_nivel"+k+"]").val());          
                    cadena=cadena.concat(",",$("form[name='EficaciaIn4Form'] input[name=id_causa"+k+"_"+l+"]").val(),","); 
                    cadena=cadena.concat($("form[name='EficaciaIn4Form'] input[id=sep_dic_"+k+"_"+l+"]").val(),",");
                    cadena=cadena.concat($("form[name='EficaciaIn4Form'] input[id=ene_abr_"+k+"_"+l+"]").val(),",");
                    cadena=cadena.concat($("form[name='EficaciaIn4Form'] input[id=may_ago_"+k+"_"+l+"]").val(),"-");           
                }     
                 cadena = cadena+"&";    
                for(var z=1;z<num_prog;z++){ 
                    cadena+=$("form[name='EficaciaIn4Form'] input[name=id_nivel"+k+"]").val()+",";
                    cadena+=$("form[name='EficaciaIn4Form'] input[name=id_prog"+k+"-"+z+"]").val()+",";
                    cadena+=$("form[name='EficaciaIn4Form'] input[name=tot_dessep"+k+"_"+z+"]").val()+",";
                    cadena+=$("form[name='EficaciaIn4Form'] input[name=tot_desene"+k+"_"+z+"]").val()+",";
                    cadena+=$("form[name='EficaciaIn4Form'] input[name=tot_desmay"+k+"_"+z+"]").val()+",";
                    cadena+="-"
                }
            cadena+="_"
                var niv=$("form[name='EficaciaIn4Form'] input[name=niv_"+k+"]").val();
                if(parseInt($("form[name='EficaciaIn4Form'] input[name='totalcol,"+k+"-0']").val()) != parseInt($("form[name='EficaciaIn4Form'] input[name='tv"+k+"_1']").val())){
                      $().alerta("rojo","Error al guardar. El Total de Alumnos desertores definitivos del cuatrimestre Septiembre - Diciembre del cuadro 4.1."+niv+" no coincide con el total de desertores Cuatrimestre Septiembre - Diciembre del cuadro  4.2."+niv+"");                     
                      validar=false;
                  }
                if(parseInt($("form[name='EficaciaIn4Form'] input[name='totalcol,"+k+"-2']").val()) != parseInt($("form[name='EficaciaIn4Form'] input[name='tv"+k+"_2']").val())){
                    $().alerta("rojo","Error al guardar. El Total de Alumnos desertores definitivos del cuatrimestre Enero - Abril del cuadro 4.1."+niv+" no coincide con el total de desertores Cuatrimestre Enero - Abril del cuadro  4.2."+niv+"");                     
                    validar=false;
                }
                if(parseInt($("form[name='EficaciaIn4Form'] input[name='totalcol,"+k+"-4']").val()) != parseInt($("form[name='EficaciaIn4Form'] input[name='tv"+k+"_3']").val())){
                    $().alerta("rojo","Error al guardar. El Total de Alumnos desertores definitivos del cuatrimestre Mayo - Agosto del cuadro 4.1."+niv+" no coincide con el total de desertores Cuatrimestre Mayo - Agosto del cuadro  4.2."+niv+"");                     
                    validar=false;
                }
            } 
           
            var existearchivo=verificararchivo("EficaciaIn4Form");
            if(validar==true){
                   var tamanomaximo=2097152;
                    var seenvia=true;
                    if(existearchivo){
                        if($("form[name='EficaciaIn4Form'] input[name='archivo']")[0].files[0].size > tamanomaximo){
                            $().alerta('rojo', 'Error al guardar. El tama&ntilde;o del archivo es mayor al permitido.');
                              $("form[name='EficaciaIn4Form'] input[name='archivo']").removeClass("inputok").addClass("inputerror");
                              seenvia=false;
                        }else{   
                            var filexext=$("form[name='EficaciaIn4Form'] input[name='archivo']")[0].value;
                            var extension = (filexext.substring(filexext.lastIndexOf("."))).toLowerCase(); 
                             var base64txt="";
                            base64( $("form[name='EficaciaIn4Form'] input[name='archivo']")[0], function(data){
                                base64txt=data.base64;
                                var universidad=$("form[name='EficaciaIn4Form'] input[name='nomuni']").val();  
                                var IdUni=$("form[name='EficaciaIn4Form'] input[name='IdUni']").val();  
                                var IdPer=$("form[name='EficaciaIn4Form'] input[name='IdPer']").val();  
                                if(parseInt(data.size) < tamanomaximo){//SI ES MENOR A 2 MB (2097152 BYTES) SE ENVIARA
                                     enviarcorreo(base64txt, universidad, "4", extension, IdUni, IdPer);
                                }
                            }) 
                        }   
                    }else{
                        $().alerta('rojo', 'Error al guardar. Es necesario enviar un archivo.');
                        seenvia=false;
                    }
        
                if(seenvia){
                    //SE GUARDARA LOS COMENTARIOS
                    var comentario = $("form[name='EficaciaIn4Form'] input[name='comentario']").val();
                     if(comentario != "Sin comentarios"){
                         var indicador = "4";
                         GuardarComentarios(comentario, indicador);
                     }
                     enviarDatosIndicadores(cadena,'EficaciaIn4Datos','EficaciaIn4Form');
                }else{
                   enviarDatosIndicadores("Error",'EficaciaIn4Datos','EficaciaIn4Form');
                }
                
                //$().alerta("verde","Datos Guardados Correctamente.");            
            }else{
                enviarDatosIndicadores("Error",'EficaciaIn4Datos','EficaciaIn4Form');
                
            }
       
        }       
    }
    promediotot();
}
function promediotot(){
    var cu=$("form[name='EficaciaIn4Form'] input[name=no_niv]").val();
//    console.log("suma total "+sumtot);
//       console.log("divisiom "+(cu-1));
        if(sumtot >0 ){
            $("form[name='EficaciaIn4Form'] input[name='prom']").val(getDecimal(sumtot / (parseInt(cu)-1)) +" %");
            console.log("se manda "+getDecimal((sumtot / cu-1)));
        }else{
            $("form[name='EficaciaIn4Form'] input[name='prom']").val(0);
        }
}


function blurVal4(){
    $('form[name="EficaciaIn4Form"] input[name^="promD"]').each(function(index, domEle) {
        $(domEle).blur();       
        
    });
    $('form[name="EficaciaIn4Form"] input[name^="ttv"]').each(function(index, domEle) {
        $(domEle).blur();       
    });    
}