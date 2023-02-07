    Dropzone.autoDiscover = false;
    var dropzonePreviewNode = document.querySelector('#dropzone-preview-list');
    dropzonePreviewNode.id = '';
    var previewTemplate = dropzonePreviewNode.parentNode.innerHTML;
    dropzonePreviewNode.parentNode.removeChild(dropzonePreviewNode);

    const dropzone = new Dropzone(".dropzone", {
    url: "/market/farm/img", // 파일을 업로드할 서버 주소 url.
    method: "post", // 기본 post로 request 감. put으로도 할수있음

    paramName: 'file', //컨트롤러 전송받는 파일 파라미터명

    previewTemplate: previewTemplate, // 만일 기본 테마를 사용하지않고 커스텀 업로드 테마를 사용하고 싶다면
    previewsContainer: '#dropzone-preview', // 드롭존 영역을 .dropzone이 아닌 다른 엘리먼트에서 하고싶을때

    autoProcessQueue: true, // 자동으로 보내기. true : 파일 업로드 되자마자 서버로 요청, false : 서버에는 올라가지 않은 상태. 따로 this.processQueue() 호출시 전송
    clickable: true, // 클릭 가능 여부
    autoQueue: true, // 드래그 드랍 후 바로 서버로 전송
    createImageThumbnails: true, //파일 업로드 썸네일 생성

    thumbnailHeight: 120, // Upload icon size
    thumbnailWidth: 120, // Upload icon size


    maxFilesize: 100000000, // 최대업로드용량 : 100MB


    uploadMultiple: false, // 다중업로드 기능
        // maxFiles: 1, // 업로드 파일수
        // parallelUploads: 10, // 동시파일업로드 수(이걸 지정한 수 만큼 여러파일을 한번에 넘긴다.)


    timeout: 300000, //커넥션 타임아웃 설정 -> 데이터가 클 경우 꼭 넉넉히 설정해주자
    dictDefaultMessage: "이미지를 드래그&드롭하거나 클릭하여 업로드 하세요",

});
