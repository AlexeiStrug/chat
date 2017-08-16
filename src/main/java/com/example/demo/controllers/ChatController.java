package com.example.demo.controllers;

import com.example.demo.entity.User;
import com.example.demo.service.MessageService;
import com.example.demo.service.UserService;
import com.example.demo.transfer.MessageDetails;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasRole('ROLE_ADMIN')")
@RestController
@RequestMapping("/chatApi")
@Api(value="chat", tags = "chat", description="Operations pertaining to chat")

public class ChatController {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private UserService userService;

    private MessageService messageService;

    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization",
                    value = "Bearer access_token",
                    required = true,
                    dataType = "string",
                    paramType = "header"),
    })
    @PostMapping("/topic/{userToId}")
    public MessageDetails send(@PathVariable Integer userToId, @RequestBody MessageDetails message) throws Exception {
        User userFromId = userService.getCurrentUser();
        messageService.handleNewMessage(userFromId.getId(), userToId, message);
        template.convertAndSend("/topic/" + userToId, message);
        return message;
    }

//    private Pageable decoratePageable(Pageable pageable, int adjustOffset) {
//        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "date"));
//        return (Pageable) Proxy.newProxyInstance(
//                ClassLoader.getSystemClassLoader(),
//                new Class[]{Pageable.class},
//                (o, method, objects) -> {
//                    if (method.getName().equals("getOffset"))
//                        return pageable.getOffset() + adjustOffset;
//                    else if (method.getName().equals("getSort"))
//                        return sort;
//                    return method.invoke(pageable, objects);
//                }
//        );
//    }

//    @GetMapping(value = "/topic/{userToId}/messages")
//    @ResponseBody
//    public Page<MessageDetails> getMessages(@PathVariable Integer userToId,
//                                            @RequestParam(defaultValue = "0") int offset, Pageable pageable) {
//        User userFromId = userService.getCurrentUser();
//        return messageService.getMessages(userFromId.getId(), userToId, decoratePageable(pageable, offset));
//    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization",
                    value = "Bearer access_token",
                    required = true,
                    dataType = "string",
                    paramType = "header"),
    })
    @GetMapping(value = "/chat/{userToId}/messages")
    @ResponseBody
    public Page<MessageDetails> getMessages(@PathVariable Integer userToId,
                                            @RequestParam(defaultValue = "0", name = "page") int page,
                                            @RequestParam(defaultValue = "20", name = "size") int size) {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "date"));
        Pageable pageable = new PageRequest(page, size, sort);
        User userFromId = userService.getCurrentUser();
        return messageService.getMessages(userFromId.getId(), userToId, pageable);
    }

    @MessageExceptionHandler
    @SendToUser("/queue/error")
    public Exception handleException(Exception ex) {
        return ex;
    }
}
