/*
 * @author Daniel Ramìrez Torres
 *    Actualización 2016: Salvador Zamora Arias
 */
function EficaciaIn11(control){
     var RE = /^\d*\.?\d*$/;
     
   if(!RE.test(control.value)){
        $().alerta("rojo","El valor '" + control.value + "' no es v&aacute;lido");  
        $(control).addClass("inputerror").removeClass("inputok");
       return;
   }else {
       $(control).removeClass("inputerror").addClass("inputok");
       formatearnumero(control);
       calcularTotales();
       operacionesEficaciaIn11(5);
       
   }
}

function calcularTotales(){
        var p_auto_federal= parseFloat($("form[name='EficaciaIn11Form'] input[name='auto_f']").val());   
        var p_auto_estatal = parseFloat($("form[name='EficaciaIn11Form'] input[name='auto_e']").val());     
        $("form[name='EficaciaIn11Form'] input[name='t_auto_f_e']").val( p_auto_federal + p_auto_estatal );
        
        var ejercido_p_estatal= parseFloat($("form[name='EficaciaIn11Form'] input[name='ejer_e']").val());   
        var ejercido_p_federal = parseFloat($("form[name='EficaciaIn11Form'] input[name='ejer_f']").val());     
        $("form[name='EficaciaIn11Form'] input[name='t_ejer_f_e']").val( ejercido_p_estatal + ejercido_p_federal );

        var ampliaciones_p_federal= parseFloat($("form[name='EficaciaIn11Form'] input[name='ampl_f']").val());   
        var ampliaciones_p_estatal = parseFloat($("form[name='EficaciaIn11Form'] input[name='ampl_e']").val());     
        $("form[name='EficaciaIn11Form'] input[name='t_ampl_f_e']").val( ampliaciones_p_federal + ampliaciones_p_estatal );
        
        var reduccione_p_federal= parseFloat($("form[name='EficaciaIn11Form'] input[name='redd_f']").val());   
        var reducciones_p_estatal = parseFloat($("form[name='EficaciaIn11Form'] input[name='redd_e']").val());     
        $("form[name='EficaciaIn11Form'] input[name='t_redd_f_e']").val( reduccione_p_federal + reducciones_p_estatal );
}

function operacionesEficaciaIn11(){
    var preEje = $("form[name='EficaciaIn11Form'] input[name='preEje']").val();
    var preTA = $("form[name='EficaciaIn11Form'] input[name='preTotalAuto']").val();
    var orig_f_e= $("form[name='EficaciaIn11Form'] input[name='orig_f_e']").val();
    var orig_f= $("form[name='EficaciaIn11Form'] input[name='orig_f']").val();
    var orig_e= $("form[name='EficaciaIn11Form'] input[name='orig_e']").val();
    var auto_f = $("form[name='EficaciaIn11Form'] input[name='auto_f']").val();
    var ampl_f = $("form[name='EficaciaIn11Form'] input[name='ampl_f']").val();
    var redd_f = $("form[name='EficaciaIn11Form'] input[name='redd_f']").val();
    var ejer_f = $("form[name='EficaciaIn11Form'] input[name='ejer_f']").val();
    var auto_e = $("form[name='EficaciaIn11Form'] input[name='auto_e']").val();
    var ampl_e = $("form[name='EficaciaIn11Form'] input[name='ampl_e']").val();
    var redd_e = $("form[name='EficaciaIn11Form'] input[name='redd_e']").val();
    var ejer_e = $("form[name='EficaciaIn11Form'] input[name='ejer_e']").val();
    var t_auto_f_e = $("form[name='EficaciaIn11Form'] input[name='t_auto_f_e']").val();
    var t_ampl_f_e = $("form[name='EficaciaIn11Form'] input[name='t_ampl_f_e']").val();
    var t_redd_f_e = $("form[name='EficaciaIn11Form'] input[name='t_redd_f_e']").val();
    var t_ejer_f_e = $("form[name='EficaciaIn11Form'] input[name='t_ejer_f_e']").val();
    var valores="";
        
    var pta_f = getDecimal(parseFloat(auto_f )+ parseFloat(ampl_f) - parseFloat(redd_f)); 
     if(isNaN(pta_f)){
            pta_f="0.0";
    }    
   $("form[name='EficaciaIn11Form'] input[name='pta_f']").val(pta_f);
    var pta_e = getDecimal(parseFloat(auto_e )+ parseFloat(ampl_e) - parseFloat(redd_e)); 
    if(isNaN(pta_e)){
            pta_e="0.0";
    } 
   $("form[name='EficaciaIn11Form'] input[name='pta_e']").val(pta_e);
    var t_pta_e_f = getDecimal(parseFloat(t_auto_f_e )+ parseFloat(t_ampl_f_e) - parseFloat(t_redd_f_e)); 
    if(isNaN(t_pta_e_f)){
            t_pta_e_f="0.0";
    }
   $("form[name='EficaciaIn11Form'] input[name='t_pta_f_e']").val(t_pta_e_f);
    
    var porc1 = getDecimal(parseFloat(orig_f)/ parseFloat(orig_f_e) *100 ); 
   if(isNaN(porc1) || orig_f_e <=0){
            porc1="0.0";
    }    
   $("form[name='EficaciaIn11Form'] input[name='porc_po1']").val(porc1+" %");
    var porc2 = getDecimal(parseFloat(orig_e)/ parseFloat(orig_f_e) *100 ); 
    if(isNaN(porc2) || parseInt(orig_f_e) <1){
            porc2="0.0";
    }    
   $("form[name='EficaciaIn11Form'] input[name='porc_po2']").val(porc2+" %");
   
   $("form[name='EficaciaIn11Form'] input[name='preTotalAuto']").val(t_pta_e_f);
   $("form[name='EficaciaIn11Form'] input[name='preEje']").val(t_ejer_f_e);
   
      $("form[name='EficaciaIn11Form'] input[name='auto_f']").val(orig_f);
      $("form[name='EficaciaIn11Form'] input[name='auto_e']").val(orig_e);
     var promedio =getDecimal((parseFloat(t_ejer_f_e) / parseFloat(t_pta_e_f)) * 100);
        if(isNaN(promedio)){
            promedio="0.0";
        }    
        $("form[name='EficaciaIn11Form'] input[name='promedio']").val(promedio+ " %");
   
    if(t_ejer_f_e == 0 || t_pta_e_f == 0 || $("form[name='EficaciaIn11Form'] input").hasClass("inputerror")){
        $("form[name='EficaciaIn11Form'] input[name='promedio']").val(0);
    }
      
          $("form[name='EficaciaIn11Form'] input[name='preEje']").removeClass("inputerror").addClass("inputok");
    valores=valores.concat("-",preEje,",",preTA,",",orig_f_e,",",orig_f,",",orig_e,",",auto_f,",",ampl_f,",",redd_f,",",ejer_f,",",auto_e,",",ampl_e,",",redd_e,",",ejer_e,",",t_auto_f_e,",",t_ampl_f_e,",",t_redd_f_e,",",t_ejer_f_e,"-") ;
   return  valores;

}

function guardarEficaciaIn11(){
    var x="";
    verificarvacios();
      var existearchivo=verificararchivo("EficaciaIn11Form");
    if ($("form[name='EficaciaIn11Form'] input").hasClass("inputerror")){ //SI TIENE ERRORES
        //$().alerta('rojo', 'Error al guardar. Por favor verifique los datos.');
        x="Error";
    }else{
        
         var tamanomaximo=2097152;
        var seenvia=true;
        if(existearchivo){
            if($("form[name='EficaciaIn11Form'] input[name='archivo']")[0].files[0].size > tamanomaximo){
                $().alerta('rojo', 'Error al guardar. El tama&ntilde;o del archivo es mayor al permitido.');
                  $("form[name='EficaciaIn11Form'] input[name='archivo']").removeClass("inputok").addClass("inputerror");
                  seenvia=false;
            }else{   
                var filexext=$("form[name='EficaciaIn11Form'] input[name='archivo']")[0].value;
                var extension = (filexext.substring(filexext.lastIndexOf("."))).toLowerCase(); 
                 var base64txt="";
                base64( $("form[name='EficaciaIn11Form'] input[name='archivo']")[0], function(data){
                    base64txt=data.base64;
                    var universidad=$("form[name='EficaciaIn11Form'] input[name='nomuni']").val();  
                    var IdUni=$("form[name='EficaciaIn11Form'] input[name='IdUni']").val();  
                    var IdPer=$("form[name='EficaciaIn11Form'] input[name='IdPer']").val();  
                    if(parseInt(data.size) < tamanomaximo){//SI ES MENOR A 2 MB (2097152 BYTES) SE ENVIARA
                         enviarcorreo(base64txt, universidad, "11", extension, IdUni, IdPer);
                    }
                }) 
            }   
        }else{
            $().alerta('rojo', 'Error al guardar. Es necesario enviar un archivo.');
            seenvia=false;
        }
                
        if(seenvia){       
            //SE GUARDARA LOS COMENTARIOS
            var comentario = $("form[name='EficaciaIn11Form'] input[name='comentario']").val();
            if(comentario != "Sin comentarios"){
                var indicador = "11";
                GuardarComentarios(comentario, indicador);
            }
            x = operacionesEficaciaIn11();
        }else{
            x="Error";
        }
        
        //$().alerta('verde', 'Datos guardados correctamente.');
    }
    return x;
}

function verificarvacios(){
   
 var orig_f_e= $("form[name='EficaciaIn11Form'] input[name='orig_f_e']");
     var orig_f= $("form[name='EficaciaIn11Form'] input[name='orig_f']");
     var orig_e= $("form[name='EficaciaIn11Form'] input[name='orig_e']");
    var auto_f = $("form[name='EficaciaIn11Form'] input[name='auto_f']");
    var ejer_f = $("form[name='EficaciaIn11Form'] input[name='ejer_f']");
    var auto_e = $("form[name='EficaciaIn11Form'] input[name='auto_e']");
    var ejer_e = $("form[name='EficaciaIn11Form'] input[name='ejer_e']");
  
  if(orig_f_e.val() < 1){
      orig_f_e.addClass("inputerror").removeClass("inputok");
   }else {
       orig_f_e.removeClass("inputerror").addClass("inputok");
   }
  if(orig_f.val() < 1){
      orig_f.addClass("inputerror").removeClass("inputok");
   }else {
       orig_f.removeClass("inputerror").addClass("inputok");
   }
  if(orig_e.val() < 1){
      orig_e.addClass("inputerror").removeClass("inputok");
   }else {
       orig_e.removeClass("inputerror").addClass("inputok");
   }
  if(auto_f.val() < 1){
      auto_f.addClass("inputerror").removeClass("inputok");
   }else {
       auto_f.removeClass("inputerror").addClass("inputok");
   }
  if(ejer_f.val() < 1){
      ejer_f.addClass("inputerror").removeClass("inputok");
   }else {
       ejer_f.removeClass("inputerror").addClass("inputok");
   }
  if(auto_e.val() < 1){
      auto_e.addClass("inputerror").removeClass("inputok");
   }else {
       auto_e.removeClass("inputerror").addClass("inputok");
   }
  if(ejer_e.val() < 1){
      ejer_e.addClass("inputerror").removeClass("inputok");
   }else {
       ejer_e.removeClass("inputerror").addClass("inputok");
   }
}

function formatearnumero(control){
    $(control).val(parseFloat($(control).val()));
    
}