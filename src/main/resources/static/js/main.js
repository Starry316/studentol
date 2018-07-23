$(function () {
    addEvents()
})

// var baseUrl = 'http://127.0.0.1:3000'
// var baseUrl = 'http://localhost:3000'
var baseUrl = 'http://www.xuzilin.com:8083'

var currentGroupId = 0

function addEvents() {
    $('.nav-item').click(function () {
        $(this).children('i').toggleClass('open')
        $(this).next().slideToggle()
    })

    $('.sub-item.member-info').click(function () {
        $('.sub-item').removeClass('selected')
        $(this).addClass('selected')
        //对于每个不同的项目，设计不同的query
        var query = {
            campus: $(this).attr('mark'),
            group: $(this).parents('ul').siblings('.nav-item').attr('mark')
        }

        loadInfoView(query)
    })

    $('.sub-item.score-input').click(function () {
        $('.sub-item').removeClass('selected')
        $(this).addClass('selected')
        //对于每个不同的项目，设计不同的query
        var query = {
            campus: $(this).attr('mark'),
            group: $(this).parents('ul').siblings('.nav-item').attr('mark')
        }
        currentGroupId = $(this).parents('ul').siblings('.nav-item').attr('mark')
        loadScoreInputView(query)
    })

    $('#showall').click(function () {
        $('.sub-item').removeClass('selected')
        loadInfoView();
    })

    $('#input-info').click(function () {
        $('.sub-item').removeClass('selected')
        $('.page-main').children().addClass('hide')
        $('.content-input-info').removeClass('hide')
    })

    $('#input-info-bt').click(function () {
        var mThis = this
        $(mThis).text('提交中...')
        var data = {}
        $('.input-panel input').each(function () {
            data[$(this).attr('name')] = $(this).val()
        })
        console.log('提交信息修改',data)
        $.post(baseUrl + '/nxms/info/add', data, function (data) {
                console.log('nxms/info/add ', data)
            $(mThis).text('提交')
            handleCommonResp(data, function(data) {
                if (data) {
                    alert('添加失败')
                } else {
                    alert('添加成功')
                }
            } )

        })
        console.log(data)
    })

    $('.content-info tbody').on('click', '.op', function () {
        var mThis = this
        var originText = $(this).text()
        $(this).text('提交中...')
        var data = {
            type: $(this).attr('name'),
            id: $(this).parent().siblings('td:nth-child(1)').text()
        }
        $.post(baseUrl + '/nxms/info/manage', data, function (data) {
            $(mThis).text(originText)
            handleCommonResp(data, function(data) {
                console.log('/nxms/manage ', data)
                if ( data ) {
                    alert('操作失败')
                } else {
                    alert('操作成功')
                }
            })
        })
    }).on('click', '.show-dt', function () {
        var index = $(this).parents('tr').index()
        showInfoDetail(currentInfo[index])
    })

    $('.content-info .details .close').click(function () {
        $('.content-info .details').addClass('hide')
    })

    $('#submit-scores').click(function () {
        var mThis = this
        var originText = $(this).text()
        $(this).text('提交中...')
        var scores = []
        $('.content-input-score tbody tr').each(function () {
            scores.push({
                id: $(this).children('td:nth-child(1)').text(),
                1: $(this).children('td').children('#score_1').val(),
                2: $(this).children('td').children('#score_2').val(),
                3: $(this).children('td').children('#score_3').val(),
                4: $(this).children('td').children('#score_4').val()
            })
        })
        var data = {
            group: currentGroupId,
            scores: scores
        }
        $.post(baseUrl + '/nxms/score', data, function (resp) {
            $(mThis).text(originText)
            handleCommonResp(resp, function(data) {
                console.log('/nxms/score POST data: ',data)
                if (data == 'success') {
                    alert('操作成功')
                } else {
                    alert('操作失败')
                }
            })
        })
    })
}

var lastQuery = {}
var currentInfo = {}
function loadInfoView(query) {
    if (query == 'useLastQuery') {
        query = lastQuery
    }
    $('.content-info tbody').html('')
    $('.page-main').children().addClass('hide')
    $('.loading').toggleClass('hide')
    $.get(baseUrl + '/nxms/info/get', query, function (resp) {
        handleCommonResp(resp, function (dataArray) {
            console.log('nxms/info/get dataArray:', dataArray)
            currentInfo = dataArray
            $('.loading').toggleClass('hide')
            $('.content-info').removeClass('hide')
            for (item in dataArray) {
                $('.content-info tbody').append(getInfoJQueryTr(dataArray[item]))
            }
            $('.content-info .table-info').html(`共 ${dataArray.length} 条数据`)
        })
        // console.log('/info/get xhr', xhr)
        // var dataArray = JSON.parse(data)
        // currentInfo = JSON.parse(data)
        // $('.loading').toggleClass('hide')
        // $('.content-info').removeClass('hide')
        // for (item in dataArray) {
        //     $('.content-info tbody').append(getInfoJQueryTr(dataArray[item]))
        // }
        // $('.content-info .table-info').html(`共 ${dataArray.length} 条数据`)
    })
}

function loadScoreInputView(query) {
    $('.content-input-score tbody').html('')
    $('.page-main').children().addClass('hide')
    $('.loading').toggleClass('hide')
    $.get(baseUrl + '/nxms/score', query, function (resp) {
        handleCommonResp(resp,function(dataArray) {
            console.log('/nxms/score GET dataArray: ', dataArray)
            for (item in dataArray) {
                $('.content-input-score tbody').append(getScoreJQueryTr(dataArray[item]))
            }
            $('.loading').toggleClass('hide')
            $('.content-input-score').removeClass('hide')
            $('.content-input-score .table-info').html(`共 ${dataArray.length} 条数据`)
        })
    })
}

function getInfoJQueryTr(data) {
    return $(
        ` <tr>
            <td>${ data.id}</td>
            <td>${ data.name}</td>
            <td>${ data.sex}</td>
            <td>${ data.stu_no}</td>
            <td>${ parseDepartment(data.department[0])}</td>
            <td>${ parseStage(data.stage[0])}</td>
            <td>
                <button class="bt bt-green op" name="2">过面</button>
                <button class="bt bt-red op" name="3">挂面</button>
            </td>
            <td>${ parseDepartment(data.department[1])}</td>
            <td>${ parseStage(data.stage[1])}</td>
            <td>
                <button class="bt bt-green op" name="2">过面</button>
                <button class="bt bt-red op" name="3">挂面</button>
            </td>
            <td>
                <button class="bt bt-green op" name="5">过试</button>
                <button class="bt bt-red op" name="4">挂试</button>
                <button class="bt bt-green show-dt" >详细信息</button>
            </td>
        </tr>`
    )
}

function getScoreJQueryTr(data) {
    return $(`
    <tr>
        <td>${data.id}</td>
        <td>${data.name}</td>
        <td><input type="number" id="score_1" value="${data[1] || 0}"></td>
        <td><input type="number" id="score_2" value="${data[2] || 0}"></td>
        <td><input type="number" id="score_3" value="${data[3] || 0}"></td>
        <td><input type="number" id="score_4" value="${data[4] || 0}"></td>
    </tr>
    `)
}

function showInfoDetail(item) {
    $('.content-info .details').removeClass('hide')
    $('#details_name').text(item.name)
    $('#details_campus').text(item.campus)
    $('#details_academy').text(item.academy)
    $('#details_from').text(item.from)
    $('#details_tel').text(item.tel)
    $('#details_qq').text(item.qq)
    $('#details_introduction').text(item.introduction)
}

function parseGroup(id) {
    switch (id) {
        case 1:
            return '技术工程事业群'
            break
        case 2:
            return '应用服务事业群'
            break
        case 3:
            return '网络媒体事业群'
            break
        case 4:
            return '团队发展事业群'
            break
        case 5:
            return '易班事业群'
            break
        default: return '无匹配'
    }
}

function parseCampus(id) {
    switch (id) {
        case 0:
            return '中心校区'
            break
        case 1:
            return '洪家楼校区'
            break
        case 2:
            return '兴隆山校区'
            break
        case 3:
            return '软件园校区'
            break
        case 4:
            return '趵突泉校区'
            break
        case 5:
            return '千佛山校区'
            break
        default: return '无匹配'

    }
}

function parseDepartment(id) {
    switch (id) {
        case 10: return '技术-视觉设计组'
        case 11: return '技术-web开发组'
        case 12: return '技术-移动开发组'
        case 20: return '应用-产品运营部'
        case 21: return '应用-内容运营部'
        case 30: return '网媒-小树林工作室'
        case 31: return '网媒-图视中心'
        case 32: return '网媒-有盐工作室'
        case 33: return '网媒-壹度工作室'
        case 34: return '网媒-新闻中心'
        case 40: return '团队-人力资源部'
        case 41: return '团队-财务部'
        case 42: return '团队-采购部'
        case 43: return '团队-运营中心'
        case 50: return '易班-生活服务事业部'
        case 51: return '易班-平台运营事业部'
        case 52: return '易班-公关推广事业部'
        default: return '无匹配'
    }
}

function parseStage(id) {
    switch (id) {
        case 1: return '已提交申请'
        case 2: return '面试通过'
        case 3: return '面试未通过'
        case 4: return '试用期未通过'
        case 5: return '试用期通过'
        default: return '无匹配'
    }
}

function handleCommonResp(commonResp, handler) {
    console.log(commonResp)
    var resp = commonResp
    if (resp.status == 200) {
        var data = resp.data
        handler(data)
    } else {
        alert('Something went wrong!CODE: ' + resp.status)
    }
} 