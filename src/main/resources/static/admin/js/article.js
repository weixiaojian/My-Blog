/**
 * Created by 13 on 2017/2/22.
 */
// Tags Input
$('#tags').tagsInput({
    width: '100%',
    height: '35px',
    defaultText: '请输入文章标签'
});

$('.toggle').toggles({
    on: true,
    text: {
        on: '开启',
        off: '关闭'
    }
});

$(".select2").select2({
    width: '100%'
});

var tale = new $.tale();

/**
 * 保存文章
 * @param status
 */
function subArticle(status) {
    var title = $('#articleForm input[name=title]').val();
    var content = $('#text').val();
    if (title == '') {
        tale.alertWarn('请输入文章标题');
        return;
    }
    if (content == '') {
        tale.alertWarn('请输入文章内容');
        return;
    }
    $('#content-editor').val(content);
    $("#articleForm #status").val(status);
    $("#articleForm #categories").val($('#multiple-sel').val());

    var params = $("#articleForm").serialize();
    var url = $('#articleForm #cid').val() != '' ? '/admin/article/modify' : '/admin/article/publish';
    tale.post({
        url:url,
        data:params,
        success: function (result) {
            if (result && result.success) {
                $("#cid").val(result.payload.cid);
                tale.alertOk({
                    text:'文章保存成功',
                    then: function () {
                        setTimeout(function () {
                            window.location.href = '/admin/article';
                        }, 500);
                    }
                });
            } else {
                tale.alertError(result.msg || '保存文章失败');
            }
        }
    });
}

var textarea = $('#text'),
    toolbar = $('<div class="markdown-editor" id="md-button-bar" />').insertBefore(textarea.parent())
preview = $('<div id="md-preview" class="md-hidetab" />').insertAfter('.markdown-editor');

markdown(textarea, toolbar, preview);


function change_switch_button(id, obj) {
    setTimeout(() => {
        let this_ = $(obj);
        let on = this_.find('.toggle-on.active').length;
        let off = this_.find('.toggle-off.active').length;
        if (on == 1) {
            $('#'+id).val(true);
        }
        if (off == 1) {
            $('#'+id).val(false);
        }
    }, 20)
}

$('div.allow-false').toggles({
    off: true,
    text: {
        on: '开启',
        off: '关闭'
    }
});