package com.teamtreehouse.techdegree.overboard.model;

import com.teamtreehouse.techdegree.overboard.exc.AnswerAcceptanceException;
import com.teamtreehouse.techdegree.overboard.exc.VotingException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by justindodson on 11/4/16.
 */
public class UserTest {
    private Board testBoard;
    private User user1;
    private User user2;
    private Question question1;
    private Question question2;
    private Answer answer1;
    private Answer answer2;

    @Before
    public void setUp() throws Exception {
        testBoard = new Board("Unit Testing") ;
        user1 = new User(testBoard, "Justin");
        user2 = new User(testBoard, "Diana");
        question1 = user1.askQuestion("Will this question get me an upvote and 5 rep points?");
        question2 = user2.askQuestion("Can user2 upvote their own quesiton?");
        answer1 = user2.answerQuestion(question1, "This is an answer to the question.");
        answer2 = user1.answerQuestion(question2, "This is user1's answer that he/she wants to upvote for more points.");

    }

    /*
    Test to make sure user rep is increased by 5
    points when their question is upvoted.
     */
    @Test
    public void userRepIncreasesWhenQuestionIsUpvoted() throws Exception {

        user2.upVote(question1);

        assertEquals(5, user1.getReputation());
    }

    /*
    Test for the extra credit portion of the project
    that makes sure the user reputation is decreased
    for downvoted answers.
     */
    @Test
    public void userRepDecreasesWhenQuestionisDownvoted() throws Exception {

        user1.upVote(answer1);
        user2.upVote(answer2);

        user1.downVote(answer1);
        user2.downVote(answer2);

        assertEquals(9, user1.getReputation());
        assertEquals(9, user2.getReputation());

    }

    /*
    Test to make sure user rep is increased by 10
    when their answer to a question is upvoted.
     */
    @Test
    public void userRepIncreasesWhenAnswerIsUpvoted() throws Exception {
        question1.addAnswer(answer1);
        user1.upVote(answer1);

        assertEquals(10, user2.getReputation());
    }

    /*
    Test to make sure a user's rep us increased
    by 15 points when their answer is accepted.
     */
    @Test
    public void anAnswerAcceptedIsWorth15Points() throws Exception {
        user1.acceptAnswer(answer1);

        assertEquals(15, user2.getReputation());

    }

    /*
    Test that makes sure the appropriate exception
    is thrown is a user tries to upvote or downvote
    their own question or answer.
     */
    @Test(expected = VotingException.class)
    public void itIsNotFairToVoteForYourself() throws Exception {
        user1.upVote(question1);
        user1.downVote(question1);
        user1.upVote(answer2);
        user1.downVote(answer2);

        user2.upVote(answer1);
        user2.downVote(answer1);
        user2.upVote(question2);
        user2.downVote(question2);
    }

    /*
    Test that ensures on the original author of
    a question can accept an answer. Ensures the
    appropriate exception is thrown.
     */
    @Test(expected = AnswerAcceptanceException.class)
    public void onlyTheOriginalAuthorCanAcceptAnAnswer() throws Exception {
        user2.acceptAnswer(answer1);
        user1.acceptAnswer(answer2);
    }
}