package io.getstream.cloud;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.Iterables;
import io.getstream.core.LookupKind;
import io.getstream.core.StreamReactions;
import io.getstream.core.exceptions.StreamException;
import io.getstream.core.http.Token;
import io.getstream.core.models.FeedID;
import io.getstream.core.models.Reaction;
import io.getstream.core.options.Filter;
import io.getstream.core.options.Limit;
import io.getstream.core.utils.DefaultOptions;
import java.util.List;
import java8.util.concurrent.CompletableFuture;

public final class CloudReactionsClient {
  private final Token token;
  private final String userID;
  private final StreamReactions reactions;

  CloudReactionsClient(Token token, String userID, StreamReactions reactions) {
    this.token = token;
    this.userID = userID;
    this.reactions = reactions;
  }

  public CompletableFuture<Reaction> get(String id) throws StreamException {
    return reactions.get(token, id);
  }

  public CompletableFuture<List<Reaction>> filter(LookupKind lookup, String id)
      throws StreamException {
    return filter(lookup, id, DefaultOptions.DEFAULT_FILTER, DefaultOptions.DEFAULT_LIMIT, "");
  }

  public CompletableFuture<List<Reaction>> filter(LookupKind lookup, String id, Limit limit)
      throws StreamException {
    return filter(lookup, id, DefaultOptions.DEFAULT_FILTER, limit, "");
  }

  public CompletableFuture<List<Reaction>> filter(LookupKind lookup, String id, Filter filter)
      throws StreamException {
    return filter(lookup, id, filter, DefaultOptions.DEFAULT_LIMIT, "");
  }

  public CompletableFuture<List<Reaction>> filter(LookupKind lookup, String id, String kind)
      throws StreamException {
    return filter(lookup, id, DefaultOptions.DEFAULT_FILTER, DefaultOptions.DEFAULT_LIMIT, kind);
  }

  public CompletableFuture<List<Reaction>> filter(
      LookupKind lookup, String id, Filter filter, Limit limit) throws StreamException {
    return filter(lookup, id, filter, limit, "");
  }

  public CompletableFuture<List<Reaction>> filter(
      LookupKind lookup, String id, Limit limit, String kind) throws StreamException {
    return filter(lookup, id, DefaultOptions.DEFAULT_FILTER, limit, kind);
  }

  public CompletableFuture<List<Reaction>> filter(
      LookupKind lookup, String id, Filter filter, Limit limit, String kind)
      throws StreamException {
    return reactions.filter(token, lookup, id, filter, limit, kind);
  }

  public CompletableFuture<Reaction> add(
      String kind, String activityID, Iterable<FeedID> targetFeeds) throws StreamException {
    return add(userID, kind, activityID, targetFeeds);
  }

  public CompletableFuture<Reaction> add(String kind, String activityID, FeedID... targetFeeds)
      throws StreamException {
    return add(userID, activityID, targetFeeds);
  }

  public CompletableFuture<Reaction> add(Reaction reaction, Iterable<FeedID> targetFeeds)
      throws StreamException {
    return add(userID, reaction, targetFeeds);
  }

  public CompletableFuture<Reaction> add(Reaction reaction, FeedID... targetFeeds)
      throws StreamException {
    return add(userID, reaction, targetFeeds);
  }

  public CompletableFuture<Reaction> add(
      String userID, String kind, String activityID, Iterable<FeedID> targetFeeds)
      throws StreamException {
    return add(userID, kind, activityID, Iterables.toArray(targetFeeds, FeedID.class));
  }

  public CompletableFuture<Reaction> add(
      String userID, String kind, String activityID, FeedID... targetFeeds) throws StreamException {
    checkNotNull(kind, "Reaction kind can't be null");
    checkArgument(!kind.isEmpty(), "Reaction kind can't be empty");
    checkNotNull(activityID, "Reaction activity id can't be null");
    checkArgument(!activityID.isEmpty(), "Reaction activity id can't be empty");

    return add(userID, Reaction.builder().activityID(activityID).kind(kind).build(), targetFeeds);
  }

  public CompletableFuture<Reaction> add(
      String userID, Reaction reaction, Iterable<FeedID> targetFeeds) throws StreamException {
    return add(userID, reaction, Iterables.toArray(targetFeeds, FeedID.class));
  }

  public CompletableFuture<Reaction> add(String userID, Reaction reaction, FeedID... targetFeeds)
      throws StreamException {
    return reactions.add(token, userID, reaction, targetFeeds);
  }

  public CompletableFuture<Reaction> addChild(
      String userID, String kind, String parentID, Iterable<FeedID> targetFeeds)
      throws StreamException {
    Reaction child = Reaction.builder().kind(kind).parent(parentID).build();
    return add(userID, child, targetFeeds);
  }

  public CompletableFuture<Reaction> addChild(
      String userID, String kind, String parentID, FeedID... targetFeeds) throws StreamException {
    Reaction child = Reaction.builder().kind(kind).parent(parentID).build();
    return add(userID, child, targetFeeds);
  }

  public CompletableFuture<Reaction> addChild(
      String userID, String parentID, Reaction reaction, Iterable<FeedID> targetFeeds)
      throws StreamException {
    Reaction child = Reaction.builder().fromReaction(reaction).parent(parentID).build();
    return add(userID, child, targetFeeds);
  }

  public CompletableFuture<Reaction> addChild(
      String userID, String parentID, Reaction reaction, FeedID... targetFeeds)
      throws StreamException {
    Reaction child = Reaction.builder().fromReaction(reaction).parent(parentID).build();
    return add(userID, child, targetFeeds);
  }

  public CompletableFuture<Void> update(String id, Iterable<FeedID> targetFeeds)
      throws StreamException {
    return update(id, Iterables.toArray(targetFeeds, FeedID.class));
  }

  public CompletableFuture<Void> update(String id, FeedID... targetFeeds) throws StreamException {
    checkNotNull(id, "Reaction id can't be null");
    checkArgument(!id.isEmpty(), "Reaction id can't be empty");

    return update(Reaction.builder().id(id).build(), targetFeeds);
  }

  public CompletableFuture<Void> update(Reaction reaction, Iterable<FeedID> targetFeeds)
      throws StreamException {
    return update(reaction, Iterables.toArray(targetFeeds, FeedID.class));
  }

  public CompletableFuture<Void> update(Reaction reaction, FeedID... targetFeeds)
      throws StreamException {
    return reactions.update(token, reaction, targetFeeds);
  }

  public CompletableFuture<Void> delete(String id) throws StreamException {
    return reactions.delete(token, id);
  }
}
