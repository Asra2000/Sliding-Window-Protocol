Sliding-Window-Protocol 
========================
## Go-Back-N ARQ
* It uses the concept of protocol pipelining i.e the sender can send multiple frames before receiving the acknowledgement for the first frame.
* There are finite number of frames and the frames are oderred in a sequential manner.
* The number of frames that can be sent depends on the window size of the sender.
* If the acknowlegement of a frame is not received within an agreed upon perio, all the frames in the current window are transmitted again.

# Working

for the sake of simplicity the frames are numbered sequentially 

<img width="397" alt="s1" src="https://user-images.githubusercontent.com/56835406/112859266-475ff380-90d0-11eb-9594-8a9dfbcf91c2.png">
<img width="406" alt="s2" src="https://user-images.githubusercontent.com/56835406/112859408-6d859380-90d0-11eb-8905-618811cb17ae.png">

Once the acknowledgement of frame 0 is received the window slides ahead and frame 4 is send
<img width="403" alt="s3" src="https://user-images.githubusercontent.com/56835406/112859511-8a21cb80-90d0-11eb-96c6-835c4dbc2a9c.png">

 Similarly all the frames are sent.
 
 If any of the acknowledgement for particular frame isn't received then all the frames in the current window are send again.
 
 <img width="402" alt="s5" src="https://user-images.githubusercontent.com/56835406/112859798-cfde9400-90d0-11eb-873a-f24b2fcb775f.png">

# Progam Output for Go-Back-N ARQ
 Case 1 : When all frames were acknowledged
 <br/>
 Client side<br/>
 <img width="397" alt="s1" src="https://user-images.githubusercontent.com/56835406/113107229-4d191e80-9221-11eb-8444-d89c6b3dbb77.jpg">
 <br/>Server side<br/>
 <img width="397" alt="s1" src="https://user-images.githubusercontent.com/56835406/113105426-307be700-921f-11eb-83ee-381c20d27996.jpg">
 
 Case 2 : When acknowledgement of any frame not received
 <br/>
 Client side<br/>
 <img width="397" alt="s1" src="https://user-images.githubusercontent.com/56835406/113106968-f9a6d080-9220-11eb-9f8a-8c22cb60462f.jpg">
 <br/>Server side<br/>
 <img width="397" alt="s1" src="https://user-images.githubusercontent.com/56835406/113106242-2d352b00-9220-11eb-9799-b8d3d537be41.jpg">

