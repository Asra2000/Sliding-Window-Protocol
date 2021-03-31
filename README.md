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
 <img width="397" alt="s1" src="https://user-images.githubusercontent.com/56835406/113190348-d0fef500-9279-11eb-8bbe-8a212891fac5.jpg">

 Case 2 : When acknowledgement of any frame not received
 <br/>
 Client side<br/>
 <img width="397" alt="s1" src="https://user-images.githubusercontent.com/56835406/113106968-f9a6d080-9220-11eb-9f8a-8c22cb60462f.jpg">
 
 All the frames in the current window are repeated.
 
 <br/>Server side<br/>
 <img width="397" alt="s1" src="https://user-images.githubusercontent.com/56835406/113190281-c2b0d900-9279-11eb-9a10-f809804afce1.jpg">
 
 <hr/>

# Selective Repeat

* The receiver acknowledges the frame and send the feedback as soon as a new frame is received.
* The sender after receiving the acknowledgement send the next frame.
* If the acknowledgement of any of the frame is not received then it resends the selective frame again to the receiver.
* The window moves ahead as soon as the acknowledgment is received.

# Working

The frames are send just like in the Go-back-n AQR

<img width="403" alt="s3" src="https://user-images.githubusercontent.com/56835406/113185984-b8401080-9274-11eb-8cd2-5c865f93b579.jpg">

If any of the frame have not be acknowledged then only that selective frame is repeated and the process repeats

<img width="403" alt="s3" src="https://user-images.githubusercontent.com/56835406/113186018-bfffb500-9274-11eb-8b37-617e24849f53.jpg">

# Progam Output for Selective Repeat ARQ
 Case 1 : When all frames were acknowledged
 <br/>
 Client side<br/>
 <img width="397" alt="s1" src="https://user-images.githubusercontent.com/56835406/113189343-bb3d0000-9278-11eb-885e-6427fb71edd0.jpg">
 <br/>Server side<br/>
 <img width="397" alt="s1" src="https://user-images.githubusercontent.com/56835406/113189392-c8f28580-9278-11eb-8e4d-f02c02e38da8.jpg">
 
 Case 2 : When acknowledgement of any frame not received
 <br/>
 Client side<br/>
 <img width="397" alt="s1" src="https://user-images.githubusercontent.com/56835406/113189452-d576de00-9278-11eb-90dd-2e7cbd63a4a7.jpg">
 
 It can be noted here that only frame 2 has been repeated and not all the frames in the current window.
 
 <br/>Server side<br/>
 <img width="397" alt="s1" src="https://user-images.githubusercontent.com/56835406/113189502-e1fb3680-9278-11eb-87d7-e5f1fcf78c8d.jpg">

<hr/>

# Comparison between the two

| Go-back-N                           |                           Selective Repeat                             |
| --------------------------------    | :---------------------------------------------------------------------:|
| In Go-Back-N Protocol, if the sent frame are find suspected then all the frames are re-transmitted from the lost packet to the last packet transmitted.	| In selective Repeat protocol, only those frames are re-transmitted which are found suspected. | 
|	Sender window size of Go-Back-N Protocol is N.|	Sender window size of selective Repeat protocol is also N.| 
|Receiver window size of Go-Back-N Protocol is 1.|	Receiver window size of selective Repeat protocol is N. | 
|Go-Back-N Protocol is less complex.	| Selective Repeat protocol is more complex.|
