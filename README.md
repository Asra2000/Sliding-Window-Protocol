Sliding-Window-Protocol 
========================
##Go-Back-N ARQ
* It uses the concept of protocol pipelining i.e the sender can send multiple frames before receiving the acknowledgement for the first frame.
* There are finite number of frames and the frames are oderred in a sequential manner.
* The number of frames that can be sent depends on the window size of the sender.
* If the acknowlegement of a frame is not received within an agreed upon perio, all the frames in the current window are transmitted again.
