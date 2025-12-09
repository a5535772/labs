// 音频文件URL（这里使用之前生成的音频链接）
const audioUrls = {
    'disney': 'https://minimax-algeng-chat-tts.oss-cn-wulanchabu.aliyuncs.com/audio%2Feffect%2F054630faf9dc22e4e5e7c61f62ba4b77.mp3?Expires=1761103740&OSSAccessKeyId=LTAI5tGLnRTkBjLuYPjNcKQ8&Signature=40aU2UAgI6AGTgFbxOvjb8ZGav0%3D',
    'zoo': 'https://minimax-algeng-chat-tts.oss-cn-wulanchabu.aliyuncs.com/audio%2Feffect%2F054630faf9dc22e4e5e7c61f62ba4b77.mp3?Expires=1761103740&OSSAccessKeyId=LTAI5tGLnRTkBjLuYPjNcKQ8&Signature=40aU2UAgI6AGTgFbxOvjb8ZGav0%3D',
    'happyvalley': 'https://minimax-algeng-chat-tts.oss-cn-wulanchabu.aliyuncs.com/audio%2Feffect%2F054630faf9dc22e4e5e7c61f62ba4b77.mp3?Expires=1761103740&OSSAccessKeyId=LTAI5tGLnRTkBjLuYPjNcKQ8&Signature=40aU2UAgI6AGTgFbxOvjb8ZGav0%3D',
    'chenghuangmiao': 'https://minimax-algeng-chat-tts.oss-cn-wulanchabu.aliyuncs.com/audio%2Feffect%2F054630faf9dc22e4e5e7c61f62ba4b77.mp3?Expires=1761103740&OSSAccessKeyId=LTAI5tGLnRTkBjLuYPjNcKQ8&Signature=40aU2UAgI6AGTgFbxOvjb8ZGav0%3D',
    'shanghaizoo': 'https://minimax-algeng-chat-tts.oss-cn-wulanchabu.aliyuncs.com/audio%2Feffect%2F054630faf9dc22e4e5e7c61f62ba4b77.mp3?Expires=1761103740&OSSAccessKeyId=LTAI5tGLnRTkBjLuYPjNcKQ8&Signature=40aU2UAgI6AGTgFbxOvjb8ZGav0%3D',
    'botanical': 'https://minimax-algeng-chat-tts.oss-cn-wulanchabu.aliyuncs.com/audio%2Feffect%2F054630faf9dc22e4e5e7c61f62ba4b77.mp3?Expires=1761103740&OSSAccessKeyId=LTAI5tGLnRTkBjLuYPjNcKQ8&Signature=40aU2UAgI6AGTgFbxOvjb8ZGav0%3D',
    'oceanpark': 'https://minimax-algeng-chat-tts.oss-cn-wulanchabu.aliyuncs.com/audio%2Feffect%2F054630faf9dc22e4e5e7c61f62ba4b77.mp3?Expires=1761103740&OSSAccessKeyId=LTAI5tGLnRTkBjLuYPjNcKQ8&Signature=40aU2UAgI6AGTgFbxOvjb8ZGav0%3D',
    'changfeng': 'https://minimax-algeng-chat-tts.oss-cn-wulanchabu.aliyuncs.com/audio%2Feffect%2F054630faf9dc22e4e5e7c61f62ba4b77.mp3?Expires=1761103740&OSSAccessKeyId=LTAI5tGLnRTkBjLuYPjNcKQ8&Signature=40aU2UAgI6AGTgFbxOvjb8ZGav0%3D',
    'aquarium': 'https://minimax-algeng-chat-tts.oss-cn-wulanchabu.aliyuncs.com/audio%2Feffect%2F054630faf9dc22e4e5e7c61f62ba4b77.mp3?Expires=1761103740&OSSAccessKeyId=LTAI5tGLnRTkBjLuYPjNcKQ8&Signature=40aU2UAgI6AGTgFbxOvjb8ZGav0%3D',
    'dianshanlake': 'https://minimax-algeng-chat-tts.oss-cn-wulanchabu.aliyuncs.com/audio%2Feffect%2F054630faf9dc22e4e5e7c61f62ba4b77.mp3?Expires=1761103740&OSSAccessKeyId=LTAI5tGLnRTkBjLuYPjNcKQ8&Signature=40aU2UAgI6AGTgFbxOvjb8ZGav0%3D'
};

// 获取音频播放器元素
const audioPlayer = document.getElementById('audio-player');

// 播放指定景点的音频
function playAudio(place) {
    const url = audioUrls[place];
    if (url) {
        // 暂停当前播放的音频
        audioPlayer.pause();
        
        // 设置新的音频源
        audioPlayer.src = url;
        
        // 显示音频控件
        audioPlayer.style.display = 'block';
        
        // 播放音频
        audioPlayer.play()
            .then(() => {
                console.log('音频开始播放');
            })
            .catch(error => {
                console.error('音频播放失败:', error);
                alert('音频播放失败，请检查网络连接或稍后重试。');
            });
    } else {
        alert('暂无该景点的音频介绍');
    }
}

// 播放完整音频
function playFullAudio() {
    playAudio('disney'); // 使用迪士尼的音频作为完整介绍
}

// 页面加载完成后隐藏音频播放器
document.addEventListener('DOMContentLoaded', function() {
    audioPlayer.style.display = 'none';
});

// 监听音频播放结束事件
audioPlayer.addEventListener('ended', function() {
    audioPlayer.style.display = 'none';
});