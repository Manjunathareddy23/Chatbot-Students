import sounddevice as sd
import soundfile as sf
from datetime import datetime

def record_wav(seconds: int = 5, samplerate: int = 16_000) -> str:
    """Records microphone for <seconds> seconds, saves to WAV, returns path."""
    filename = f"audio_{datetime.now().strftime('%Y%m%d_%H%M%S')}.wav"
    audio = sd.rec(int(seconds * samplerate), samplerate=samplerate,
                   channels=1, dtype='int16')
    sd.wait()
    sf.write(filename, audio, samplerate)
    return filename
