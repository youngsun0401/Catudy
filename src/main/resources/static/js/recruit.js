// 오류 있어서 사용 X 나중에 확인


// var writeTime = $('#post-date').val();
// let nowTime = new Date();
// var time = new Date(writeTime);

// console.log(writeTime);

// // console.log(nowTime.getFullYear());

// // console.log(time.getFullYear());
// // console.log(time.getMonth() + 1);
// // console.log(time)
// // console.log(time.getDate());


// var minus;

// if (nowTime.getFullYear() > time.getFullYear()) {
//     minus = nowTime.getFullYear() - time.getFullYear();
//     console.log(minus + "년 전");
//     $('.recruit-badge-date').html(minus + "년 전");
// } else if (nowTime.getMonth() > time.getMonth() +1) {
//     minus = nowTime.getMonth() - time.getMonth() +1;
//     console.log(minus + "달 전");
//     $('.recruit-badge-date').html(minus + "달 전");
// } else if (nowTime.getDate() > time.getDate()) {
//     minus = nowTime.getDate() - time.getDate();
//     console.log(minus + "일 전");
//     $('.recruit-badge-date').html(minus + "일 전");
// } else if (nowTime.getDate() == time.getDate()) {
//     var now = nowTime.getTime();
//     var write = time.getTime();
//     if (now > write) {
//         sec = parseInt(now - write) / 1000;
//                 day  = parseInt(sec/60/60/24);
//                 sec = (sec - (day * 60 * 60 * 24));
//                 hour = parseInt(sec/60/60);
//                 sec = (sec - (hour*60*60));
//                 min = parseInt(sec/60);
//                 sec = parseInt(sec-(min*60));
//                 if (hour > 0) {
//                     console.log(hour + "시간 전");
//                     $('.recruit-badge-date').html(hour + "시간 전");
//                 } else if (min > 0) {
//                     console.log(min + "분 전");
//                     $('.recruit-badge-date').html(min + "분 전");
//                 } else if (sec > 0) {
//                     console.log(sec + "초 전");
//                     $('.recruit-badge-date').html(sec + "초 전");
//                 }
//     }
// }