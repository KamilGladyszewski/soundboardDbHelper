function ytVidId(url) {
    var p = /^(?:https?:\/\/)?(?:www\.)?(?:youtu\.be\/|youtube\.com\/(?:embed\/|v\/|watch\?v=|watch\?.+&v=))((\w|-){11})(?:\S+)?$/;
    return (url.match(p)) ? RegExp.$1 : false;
  }

submitOn = true;

$(document).ready(function(){

    $(".form-control").focusout(function(){
        $(this).parent().removeClass('has-error'); // remove the error class
        $(this).parent().children(".help-block").remove();

        if($(this).val() == ""){
            $(this).parent()
                .addClass('has-error')
                .append('<div class="help-block"> pole nie może być puste </div>');
        }else{
            if($(this).parent().hasClass("link-group")){
                if(!ytVidId(document.getElementById('input-link').value)){
                    submitOn = false;
                    $('#link-group')
                        .addClass('has-error')
                        .append('<div class="help-block"> wprowadź poprawny link </div>');
                }else{
                    submitOn = true;
                }
            }
        }
            
    });
 
    $("[name='form-send']").submit(function(e){     
        
        if(submitOn){
            $('.form-group').removeClass('has-error'); // remove the error class
            $('.help-block').remove(); 

            var formData = {
                'link': $('input[name=link]').val(),
                'txt': $('input[name=txt]').val(),
                'time': $('input[name=time]').val(),
                'id' : ytVidId($('input[name=link]').val())
            };

        

            $.ajax({
                type: "POST",
                url: "script.php",
                data: formData,
                dataType: 'json',
                encode: true
            }).done(function(data){
                console.log(data);
                if(!data.success){
                    if(data.errors.link){
                        $("#link-group")
                            .addClass('has-error')
                            .append('<div class="help-block"> pole nie może być puste </div>');
                    }
                    if(data.errors.txt){                   
                        $("#txt-group")
                            .addClass('has-error')
                            .append('<div class="help-block"> pole nie może być puste </div>');
                    }
                    if(data.errors.time){                   
                        $("#time-group")
                            .addClass('has-error')
                            .append('<div class="help-block"> pole nie może być puste </div>');
                    }
                }else{
                    alert("Dziękujemy! Twój dźwięk został przyjęty i zostanie przez nas sprawdzony!")
                }
            });
        }
       
        e.preventDefault();
        
    });
    
});
