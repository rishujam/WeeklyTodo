//
//  ContentView.swift
//  iosApp
//
//  Created by Sudhanshu Kumar  on 09/09/25.
//
import shared
import SwiftUI

struct ContentView: View {
    var body: some View {
        VStack {
            Image(systemName: "globe")
                .imageScale(.large)
                .foregroundStyle(.tint)
            Text(ComWeeklyTodoGreeting().greet())
        }
        .padding()
    }
}

#Preview {
    ContentView()
}
