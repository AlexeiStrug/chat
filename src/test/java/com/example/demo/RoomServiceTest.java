//package com.example.demo;
//
//import com.example.demo.entity.Room;
//import com.example.demo.entity.User;
//import com.example.demo.repositories.RoomRepository;
//import com.example.demo.repositories.UserRepository;
//import com.example.demo.service.RoomService;
//import com.example.demo.service.RoomServiceImpl;
//import com.example.demo.service.UserService;
//import com.example.demo.utils.SecurityUtils;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mock;
//
//import java.util.Optional;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
///**
// * Created by Alex on 15.08.2017.
// */
//public class RoomServiceTest extends AbstractServiceTest {
//
//    @Mock
//    private RoomRepository roomRepository;
//
//    @Mock
//    private UserService userService;
//
//    @Mock
//    private SecurityUtils securityUtils;
//
//    @Mock
//    private UserRepository userRepository;
//
//    private RoomService roomService;
//
//    @Before
//    public void setUp() throws Exception {
//        roomService = new RoomServiceImpl(
//                roomRepository,
//                userService,
//                userRepository,
//                securityUtils
//        );
//    }
//
//
//    @Test
//    public void createRoom() throws Exception {
//        User userFrom = new User();
//        userFrom.setId(5);
//        userFrom.setUsername("userFrom");
//
//        User userTo = new User();
//        userTo.setId(6);
//        userTo.setUsername("userTo");
//        Room newRoom = roomService.createRoom(userFrom, userTo);
//
//        assertEquals("userFrom", newRoom.getFirstUser().getUsername());
//        assertEquals("userTo", newRoom.getSecondUser().getUsername());
//    }
//
//    @Test
//    public void deleteRoom() throws Exception {
//        userRepository = mock(UserRepository.class);
//
//        when(securityUtils.getCurrentUserLogin()).thenReturn("userFrom");
//        User userForm = setupUserFrom();
//        User userTo = setupUserTo();
//
//        Room room = new Room();
//        room.setId(100);
//        room.setFirstUser(userForm);
//        room.setSecondUser(userTo);
//
//        when(roomRepository.findOne(100)).thenReturn(room);
//        when(securityUtils.getCurrentUserLogin()).thenReturn("userFrom");
//        roomService.deleteRoom(100);
//        verify(roomRepository).delete(room);
//    }
//
//    private User setupUserFrom() {
//        User userFrom = new User();
//        userFrom.setId(5);
//        userFrom.setUsername("userFrom");
//        when(userService.getCurrentUser()).thenReturn(Optional.of(userFrom).get());
//        return userFrom;
//    }
//
//    private User setupUserTo() {
//        User userTo = new User();
//        userTo.setId(6);
//        userTo.setUsername("userTo");
//        when(userService.getCurrentUser()).thenReturn(Optional.of(userTo).get());
//        return userTo;
//    }
//}
