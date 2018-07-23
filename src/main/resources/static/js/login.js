$(function () {
    $('#login-bt').click(function () {
        $('.login-loading').toggleClass('hide')
        var token = $('input').val()
        $.ajax({
            type: 'POST',
            url: baseUrl + '/nxms/login',
            data: {'token': token},
            success: function(resp, status, xhr) {
                handleCommonResp(resp, function(data) {
                    $('.login-loading').toggleClass('hide')
                    if(data == 'success') {
                        $('.main').toggleClass('hide')
                        $('.login').toggleClass('hide')
                    } else {
                        alert('无效的口令')
                    }
                })
            }
        })
    })
})