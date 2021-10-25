package com.vbt.kanban.service;

import com.vbt.kanban.account.service.AccountService;
import com.vbt.kanban.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MentionExtractorImpl implements MentionExtractor {
    private final Pattern pattern = Pattern.compile("(?<=^|(?<=[^a-zA-Z0-9-\\.]))@[A-Za-z0-9-]+(?=[^a-zA-Z0-9-_\\.])");
   // private final Pattern pattern = Pattern.compile("([a-zA-Z0-9+._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+)");

    @Autowired
    private AccountService accountService;

    @Override
    public List<Account> extract(String text) {
        Matcher matcher = pattern.matcher(text);

        return matcher.results()
                .map(r -> r.group().replace("@", ""))
                // .map(mr -> mr.group())
                .map(r -> accountService.findByNickName(r))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
}
