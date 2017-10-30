/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function () {
    $('#frmCategory').validate({
        rules: {
            categoryname: {
                minlength: 3,
                maxlength: 50,
                required: true
            }
        },
        messages: {
            categoryname: {
                minlength: 'Debe tener más de tres caracteres.',
                maxlength: 'Debe tener menos de 50 caracteres.',
                required: 'El nombre de la categoría es obligatorio.'
            }
        },
        highlight: function (element) {
            $(element).closest('.form-group').addClass('has-error');
        },
        unhighlight: function(element) {
            $(element).closest('.form-group').removeClass('has-error');
        },
        errorElement: 'span',
        errorClass: 'help-block',
        errorPlacement: function (error, element) {
            error.insertAfter(element.parent());
        },
        submitHandler: function (form) {
            newCategory();
            return false;
        }
    });
    
    $('#frmEditCategory').validate({
        rules: {
            categoryname: {
                minlength: 3,
                maxlength: 50,
                required: true
            }
        },
        messages: {
            categoryname: {
                minlength: 'Debe tener más de tres caracteres.',
                maxlength: 'Debe tener menos de 50 caracteres.',
                required: 'El nombre de la categoría es obligatorio.'
            }
        },
        highlight: function (element) {
            $(element).closest('.form-group').addClass('has-error');
        },
        unhighlight: function(element) {
            $(element).closest('.form-group').removeClass('has-error');
        },
        errorElement: 'span',
        errorClass: 'help-block',
        errorPlacement: function (error, element) {
            error.insertAfter(element.parent());
        },
        submitHandler: function (form) {
            updateCategory();
            return false;
        }
    });
    
    $('#tbcategories').DataTable({
        language: {
            url: '//cdn.datatables.net/plug-ins/1.10.12/i18n/Spanish.json'
        },
        ajax: {
            url: 'ConsultaCategorias',
            dataSrc: function (json) {
                return $.parseJSON(json.detail);
            }
        },
        columns: [
            {
                data: function (row) {
                    return "<div align = 'center'>" + row["categoryid"] + "</div>";
                }   
            },
            { data: 'categoryname' },
            {
                data: function (row) {
                    var str = "<div align = 'center'>";
                    str += "<button class = 'btn btn-danger btn-xs'\n\
                            onclick = 'deleteCategory(" + row["categoryid"] + ")'><span\n\
                            class = 'glyphicon glyphicon-trash' \n\
                            aria-hidden = 'true'></span></button>";
                    str += "&nbsp;<button class = 'btn btn-info btn-xs' \n\
                            onclick = 'showCategory(" + row["categoryid"] + ",\"" + row["categoryname"] + "\")'>\n\
                            <span class = 'glyphicon glyphicon-pencil' \n\
                            aria-hidden = 'true'></span></button>";
                    str += "</div>";
                    return str;
                }
            }
        ]
    });
    
    $('#btnModificar').click(function () {
        $('#frmEditCategory').submit();
    });
});

function newCategory () {    
    $.ajax({
       url: 'CrearCategoria',
       type: 'post',
       data: $('#frmCategory').serialize()
    })
    .done(function (data) {
        $.growl.notice({
            title: '¡Exitoso!',
            message: data.message
        });
        $('#categoryname').val('');
        $('#tbcategories').dataTable().api().ajax.reload(null, false);
    })
    .fail(function (data) {
        $.growl.error({
            title: '¡Error!',
            message: data.responseJSON.message
        });
    });
}

function deleteCategory(id) {
    swal({
        title: '¿Estás seguro de eliminar la categoría?',
        text: "La operación es irreversible",
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Eliminar',
        cancelButtonText: 'Cancelar'
    }).then(function () {
        $.ajax({
            url: 'EliminarCategoria',
            type: 'post',
            data: { id: id }
        })
        .done(function (data) {
            console.log(data);
            swal(
                '¡Exitoso!',
                data.message,
                'success'
                );
            $('#tbcategories').dataTable().api().ajax.reload(null, false);
        })
        .fail(function (data) {
            swal(
                '¡Error!',
                data.responseJSON.message,
                'error'
                );
        });
    });
}

function showCategory(id, name) {
   $('#categoryid').val(id);
    $('#categoryname2').val(name);
    $('#modalCategory').modal('show');
}

function updateCategory(){
    $.ajax({
        url: 'ActualizarCategoria',
        type: 'post',
        data: {
            categoryid: $('#categoryid').val(),
            categoryname: $('#categoryname2').val()
        }
    })
    .done(function (data) {
        $.growl.notice({
            title: '¡Exitoso!',
            message: data.message
        });
        $('#tbcategories').dataTable().api().ajax.reload(null, false);
        $('#modalCategory').modal('hide');
    })
    .fail(function (data) {
        $.growl.error({
            title: '¡Error!',
            message: data.responseJSON.message
        });
    });    
}