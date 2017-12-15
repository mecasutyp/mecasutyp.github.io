//    Document   : Script PertinenciaIn22
//    Author     : Daniel Ramìrez Torres

function calcularTa3(cuadro)
{
    var totprogs= $("form[name='PertinenciaIn22Form'] input[name='noProg"+cuadro+"']").val();
    var totalno=$("form[name='PertinenciaIn22Form'] input[name='total2"+cuadro+"']").val();
    
    var p1=(parseFloat(totalno)/parseFloat(totprogs))*100;
    p1 = getDecimal(p1);
    if(isNaN(p1)){
        p1="0.0";
    }
    $("form[name='PertinenciaIn22Form'] input[name='porcentaje"+cuadro+"']").val(p1+' %');
    
    //total31
    
    var tot2= $("form[name='PertinenciaIn22Form'] input[name='total3"+cuadro+"']").val();
    var totalAlumnos= $("form[name='PertinenciaIn22Form'] input[name='total1"+cuadro+"']").val();
    
   /*cuadro 2*/
    
    var p2=(parseFloat(tot2)/parseFloat(totalAlumnos))*100;
    p2 = getDecimal(p2);
    if(isNaN(p2)){
        p2="0.0";
    }
    $("form[name='PertinenciaIn22Form'] input[name='porcentajes"+cuadro+"']").val(p2+' %');
}

function valores22(){
    var cadena="x";
    var noCuadros=$("form[name='PertinenciaIn22Form'] input[name='noCuadros']").val();
    for(var l=1;l<=noCuadros;l++){
        var noProg=$("form[name='PertinenciaIn22Form'] input[name='noProg"+l+"']").val();
        for(var j=1;j<=noProg;j++){
            cadena=cadena.concat($("form[name='PertinenciaIn22Form'] input[name='id"+l+j+"']").val(),",",$("form[name='PertinenciaIn22Form'] input[name='matricula"+l+j+"']").val(),",",$("form[name='PertinenciaIn22Form'] input[name='matricula_pert"+l+j+"']").val(),",");
            for(var i=0;i<$("form[name='PertinenciaIn22Form'] input[name='radioss"+l+j+"']").length;i++){
                if ($("form[name='PertinenciaIn22Form'] input[name='radioss"+l+j+"']")[i].checked==true){
                    cadena=cadena.concat($("form[name='PertinenciaIn22Form'] input[name='radioss"+l+j+"']")[i].value,",");
                }    
            }
            cadena=cadena.concat("'",$("form[name='PertinenciaIn22Form'] select[name='fecha_inicio"+l+j+"']").val(),"'",",","'",$("form[name='PertinenciaIn22Form'] select[name='fecha_estudio"+l+j+"']").val(),"'",",","'",$("form[name='PertinenciaIn22Form'] select[name='fecha_trabajo"+l+j+"']").val(),"'",",");
            cadena=cadena.concat("x");
        }
    }
    
    return(cadena);
}

function operacionesPertinencia22a(cuadro){ 
    var total1=0,total2=0,total3=0;
    var noProg=$("form[name='PertinenciaIn22Form'] input[name='noProg"+cuadro+"']").val();
    for(var j=1;j<=noProg;j++){
        total1=parseInt(total1)+parseInt($("form[name='PertinenciaIn22Form'] input[name='matricula"+cuadro+j+"']").val());
        total3=parseInt(total3)+parseInt($("form[name='PertinenciaIn22Form'] input[name='matricula_pert"+cuadro+j+"']").val());
        for(var i=0;i<$("form[name='PertinenciaIn22Form'] input[name='radioss"+cuadro+j+"']").length;i++){
            if ($("form[name='PertinenciaIn22Form'] input[name='radioss"+cuadro+j+"']")[i].checked==true){
                if($("form[name='PertinenciaIn22Form'] input[name='radioss"+cuadro+j+"']")[i].value==1){
                    total2=parseInt(total2)+parseInt($("form[name='PertinenciaIn22Form'] input[name='radioss"+cuadro+j+"']")[i].value);
                    $("form[name='PertinenciaIn22Form'] input[name='matricula_pert"+cuadro+j+"']").removeAttr("disabled");
                        if(parseInt($("form[name='PertinenciaIn22Form'] input[name='matricula_pert"+cuadro+j+"']").val())>parseInt($("form[name='PertinenciaIn22Form'] input[name='matricula"+cuadro+j+"']").val())){
                           $().alerta("amarillo","Cuidado!, La matr&iacute;cula pertinente no puede ser mayor al numero de matr&iacute;cula inicial"); 
                        }
                    } else{
                    total2=parseInt(total2)+parseInt($("form[name='PertinenciaIn22Form'] input[name='radioss"+cuadro+j+"']")[i].value);
                    $("form[name='PertinenciaIn22Form'] input[name='matricula_pert"+cuadro+j+"']").attr("disabled","disabled");
                    total3=total3-parseInt($("form[name='PertinenciaIn22Form'] input[name='matricula_pert"+cuadro+j+"']").val());
                    $("form[name='PertinenciaIn22Form'] input[name='matricula_pert"+cuadro+j+"']").val("0");
                }
            }    
        }
    }
    if (!$("form[name='PertinenciaIn22Form'] input").hasClass("inputerror")){
        $("form[name='PertinenciaIn22Form'] input[name='total1"+cuadro+"']").val(total1);
        $("form[name='PertinenciaIn22Form'] input[name='total2"+cuadro+"']").val(total2);
        $("form[name='PertinenciaIn22Form'] input[name='total3"+cuadro+"']").val(total3);
        total1=0,total2=0,total3=0;
    }else{
        $("form[name='PertinenciaIn22Form'] input[name='total1"+cuadro+"']").val(0);
        $("form[name='PertinenciaIn22Form'] input[name='total2"+cuadro+"']").val(0);
        $("form[name='PertinenciaIn22Form'] input[name='total3"+cuadro+"']").val(0);
    }
    
    calcularTa3(cuadro);

}
function verificapertinencia(cuadro, programa){
    var anio_inicio=parseInt($("#fecha_inicio"+cuadro+""+programa+" option:selected").text());
    var anio_estudio=parseInt($("#fecha_estudio"+cuadro+""+programa+" option:selected").text());
    var anio_trabajo=parseInt($("#fecha_trabajo"+cuadro+""+programa+" option:selected").text());
    var f = new Date();
    var anio_encurso=parseInt(f.getFullYear());
    var pertinentees=true;
    var pertinentetrabajo=true;
    if((anio_estudio+5) < (anio_encurso)){
        pertinentees=false;
    }else{
        pertinentees=true;
    }
    if((anio_trabajo+3) < (anio_encurso)){
        pertinentetrabajo=false;
    }else{
        pertinentetrabajo=true;
    }
    
    if(pertinentees || pertinentetrabajo){
      $("#radios"+cuadro+""+programa+"2").prop("checked", false);
      $("#radios"+cuadro+""+programa+"1").prop("checked", true);
      $("form[name='PertinenciaIn22Form'] input[name='matricula_pert"+cuadro+""+programa+"']").removeAttr("disabled");
        operacionesPertinencia22a(cuadro);    
    }else{
      $("#radios"+cuadro+""+programa+"1").prop("checked", false);
      $("#radios"+cuadro+""+programa+"2").prop("checked", true);
      $("form[name='PertinenciaIn22Form'] input[name='matricula_pert"+cuadro+""+programa+"']").attr("disabled","disabled");
      operacionesPertinencia22a(cuadro);
    }
}
function indicador22(control,cuadro){
    if (control.value == "")
        control.value = 0;
    if (!/^([0-9])*$/.test(control.value)){
        $().alerta("amarillo", "El valor '"+ control.value +"' no es v&aacute;lido");
        $(control).removeClass("inputok").addClass("inputerror");
        return;
    } else{
        $(control).removeClass("inputerror").addClass("inputok");
        control.value=Number(control.value);
        operacionesPertinencia22a(cuadro);
    }
}

function guardarPertinenciaIn22(){
    var ale = 0;
    var x="";
    var existearchivo=verificararchivo("PertinenciaIn22Form");
    $('form[name="PertinenciaIn22Form"] input[name^="total1"]').each(function(index, domEle) {
        var cuad = $(domEle).attr("name").substring($(domEle).attr("name").length - 1, $(domEle).attr("name").length);
        if($(domEle).val() != $("form[name='PertinenciaIn22Form'] input[name^='mat1"+cuad+"']").val()){
            var nivel=$("form[name='PertinenciaIn22Form'] input[name^='idNivel"+cuad+"']").val();
            $().alerta("amarillo","Cuidado!, La matr&iacute;cula inicial total del cuadro 22."+nivel+" no coincide con la matr&iacute;cula inicial del nivel seg&uacute;n la CGUT ('"+$("form[name='PertinenciaIn22Form'] input[name^='mat1"+cuad+"']").val()+"')");
            ale++;
        }
    });
    if(ale != 0){
        return;
    }
    if ($("form[name='PertinenciaIn22Form'] input").hasClass("inputerror")){
        //  $().alerta('rojo', 'Error al guardar. Por favor verifique los datos.');
        x="Error";
    }else{
         var tamanomaximo=2097152;
         var enviar = true;
          if(existearchivo){
            if($("form[name='PertinenciaIn22Form'] input[name='archivo']")[0].files[0].size > tamanomaximo ){
                $().alerta('rojo', 'Error al guardar. El tama&ntilde;o del archivo es mayor al permitido.');
                  $("form[name='PertinenciaIn18Form'] input[name='archivo']").removeClass("inputok").addClass("inputerror");
               enviar=false;
           }
           
             var filexext=$("form[name='PertinenciaIn22Form'] input[name='archivo']")[0].value;
                var extension = (filexext.substring(filexext.lastIndexOf("."))).toLowerCase(); 
                var base64txt="";
                base64( $("form[name='PertinenciaIn22Form'] input[name='archivo']")[0], function(data){
                    base64txt=data.base64;
                  var universidad=$("form[name='PertinenciaIn22Form'] input[name='nomuni']").val();  
                    var IdUni=$("form[name='PertinenciaIn22Form'] input[name='IdUni']").val();  
                    var IdPer=$("form[name='PertinenciaIn22Form'] input[name='IdPer']").val();
                    if(parseInt(data.size) < tamanomaximo){//SI ES MENOR A 2 MB (2097152 BYTES) SE ENVIARA
                        enviarcorreo(base64txt, universidad, "22", extension, IdUni, IdPer);
                   }
               })
        }else{
            $().alerta('rojo', 'Error al guardar. Es necesario enviar un archivo.');
            enviar=false;
        }
        if(enviar){ 
            x = valores22();
               //SE GUARDARA LOS COMENTARIOS
           var comentario = $("form[name='PertinenciaIn22Form'] input[name='comentario']").val();
            if(comentario != "Sin comentarios"){
                var indicador = "22";
                GuardarComentarios(comentario, indicador);
            }
        }else{
            x="Error";
        }
    //$().alerta('verde', 'Datos guardados correctamente.');        
    }
    return x;
}

function operacionesblur22(){
    $('form[name="PertinenciaIn22Form"] input[name^="matricula"]').each(function(index, domEle) {
        if($(domEle).val() != "0"){
            $(domEle).blur();
        }
    });
    
}