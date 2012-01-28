package org.samplr.server.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;


// (Equivalence, Case Equivalence, Word Membership Differences, Letter Membership Differences, Punctuation Membership Differences)

/**
 * 
 * @author mtp
 *
 */

public class Ranker {
  private final String needle;

  public Ranker(final String needle) {
    this.needle = needle;
  }

  private String sanitizeString(final String input) {
    return input.trim().replaceAll("\\s+", " ").toLowerCase();
  }

  private Candidate makeCandidate(final String candidateText) {
    final List<Integer> differences = new ArrayList<Integer>();

    final boolean equivalenceMatch = needle.equals(candidateText);
    final boolean caseInsensitiveMatch = needle.equalsIgnoreCase(candidateText);

    differences.add(equivalenceMatch ? 0 : 1);
    differences.add(caseInsensitiveMatch ? 0 : 1);

    final String sanitizedNeedleText = sanitizeString(needle);
    final String sanitizedCandidateText = sanitizeString(candidateText);

    final boolean sanitizedMatch = sanitizedNeedleText.equals(sanitizedCandidateText);

    differences.add(sanitizedMatch ? 0 : 1);

    final Predicate<String> nonEmptyPredicate = new Predicate<String>() {
      @Override
      public boolean apply(final String candidate) {
        return !Strings.isNullOrEmpty(candidate);
      }
    };

    final List<String> needleWords = Lists.newArrayList(Iterables.filter(Arrays.asList(sanitizedNeedleText.split("[\\s+\\p{P}]")), nonEmptyPredicate));
    final List<String> candidateWords = Lists.newArrayList(Iterables.filter(Arrays.asList(sanitizedCandidateText.split("[\\s+\\p{P}]")), nonEmptyPredicate));

    final boolean sanitizedWordsMatch = needleWords.equals(candidateWords);

    differences.add(sanitizedWordsMatch ? 0 : 1);

    final Set<String> needleWordsSet = new HashSet<String>(needleWords);
    final Set<String> candidateWordsSet = new HashSet<String>(candidateWords);

    final int wordMembershipDifference = Sets.symmetricDifference(needleWordsSet, candidateWordsSet).size();

    differences.add(wordMembershipDifference);

    final List<String> needleLetters = Arrays.asList(sanitizedNeedleText.replaceAll("[\\s\\p{P}]", "").split(""));
    final List<String> candidateLetters = Arrays.asList(sanitizedCandidateText.replaceAll("[\\s\\p{P}]", "").split(""));

    final Set<String> needleLetterSet = new HashSet<String>(needleLetters);
    final Set<String> candidateLetterSet = new HashSet<String>(candidateLetters);

    final int letterMembershipDifference = Sets.symmetricDifference(needleLetterSet, candidateLetterSet).size();

    differences.add(letterMembershipDifference);

    final Pattern notPunctuationPattern = Pattern.compile("[^\\p{P}]");

    final List<String> needlePunctuation = Arrays.asList(notPunctuationPattern.split(sanitizedNeedleText));
    final List<String> candidatePunctuation = Arrays.asList(notPunctuationPattern.split(sanitizedCandidateText));

    final Set<String> needlePunctuationSet = new HashSet<String>(needlePunctuation);
    final Set<String> candidatePunctuationSet = new HashSet<String>(candidatePunctuation);

    final int punctuationMembershipDifferences = Sets.symmetricDifference(needlePunctuationSet, candidatePunctuationSet).size();

    differences.add(punctuationMembershipDifferences);

    return new Candidate(candidateText, differences);
  }

  private final class Candidate implements Comparable<Candidate> {
    private final String candidateText;
    private final List<Integer> differences;

    public Candidate(final String candidateText, final List<Integer> differences) {
      this.candidateText = candidateText;
      this.differences = differences;
    }

    @Override
    public int compareTo(final Candidate other) {
      final List<Integer> otherDifferences = other.getDifferences();

      for (int i = 0; i < differences.size(); i++) {
        final Integer myDifference = differences.get(i);
        final Integer otherDifference = otherDifferences.get(i);

        final int compareToResult = myDifference.compareTo(otherDifference);
        if (compareToResult != 0) {
          return compareToResult;
        }
      }

      return 0;
    }

    ImmutableList<Integer> getDifferences() {
      return ImmutableList.copyOf(differences);
    }

    String getText() {
      return candidateText;
    }
  }

  public List<String> rank(final List<String> candidates) {
    final List<Candidate> convertedCandidates = new ArrayList<Candidate>(candidates.size());

    for (final String candidate : candidates) {
      convertedCandidates.add(makeCandidate(candidate));
    }

    Collections.sort(convertedCandidates);

    final ImmutableList.Builder<String> emission = ImmutableList.builder();

    for (final Candidate candidate : convertedCandidates) {
      emission.add(candidate.getText());
    }

    return emission.build();
  }
}
