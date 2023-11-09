// Copyright 2013 The Flutter Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

import 'package:flutter/widgets.dart';
import 'package:go_router/go_router.dart';
import 'package:kakao_flutter_sdk_user/kakao_flutter_sdk_user.dart';
import 'package:flutter/services.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:p_on/screen/main/user/fn_kakao.dart';
import 'package:p_on/screen/main/user/token_state.dart';

// 로그인 상태 파악

import 'package:flutter/services.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
// import 'package:p_on/screen/main/user/fn_kakao.dart';
// import 'package:p_on/screen/main/user/token_state.dart';


/// A mock authentication service.
class PonAuth extends ChangeNotifier {
  // 로그인 상태
  bool _signedIn = false;

  /// Whether user has signed in.
  bool get signedIn => _signedIn;

  // /// Signs in a user.
  // 로그인
  // 서버 토큰이 있으면, 카카오 로그인 -> 서버 토큰 발급 진행
  Future<bool> signInWithKakao(WidgetRef ref) async {
    print('signInWithKakao');
    final token = ref.read(loginStateProvider).serverToken;
    final role = ref.read(loginStateProvider).role;

    if (token != null && (role == 'USER' || role == 'GUEST')) {
      // await kakaoLogin(ref);
      // await fetchToken(ref);
      print('사인트루');
      print(_signedIn);
      _signedIn = true;
      print(_signedIn);
      notifyListeners();
      return _signedIn;
    } else {
      print('signWithKakao');
      print(token);
      print(role);
      _signedIn = false;
    }
    // 상태 변경을 리스너에게 알림
    notifyListeners();
  }

  Future<void> signOut() async {
    // 로그아웃 처리
    _signedIn = false;
    notifyListeners();
  }

  String? guard(BuildContext context, GoRouterState state) {
    final bool signedIn = this.signedIn;
    final bool signingIn = state.matchedLocation == '/signin';
    final bool registeringIn = state.matchedLocation =='/register';

    // Go to /signin if the user is not signed in
    if (!signedIn && (!signingIn || !registeringIn)) {
      print('_signedIn값이 나온다.');
      print(_signedIn);
      print(signingIn);
      print(registeringIn);
      return '/signin';
    }
    // Go to /books if the user is signed in and tries to go to /signin.
    else if (signedIn && (!signingIn || !registeringIn)) {
      return '/';
    }

    // no redirect
    return null;
  }
}

/// An inherited notifier to host [PonAuth] for the subtree.
class PonAuthScope extends InheritedNotifier<PonAuth> {
  /// Creates a [PonAuthScope].
  const PonAuthScope({
    required PonAuth super.notifier,
    required super.child,
    super.key,
  });

  /// Gets the [PonAuth] above the context.
  static PonAuth of(BuildContext context) =>
      context.dependOnInheritedWidgetOfExactType<PonAuthScope>()!.notifier!;
}
