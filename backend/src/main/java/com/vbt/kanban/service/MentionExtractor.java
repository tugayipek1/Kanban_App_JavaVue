package com.vbt.kanban.service;

import com.vbt.kanban.entity.Account;

import java.util.List;

public interface MentionExtractor {

   List<Account> extract(String text);
}
